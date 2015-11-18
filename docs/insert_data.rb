require 'nokogiri'
require 'json'
require 'set'
require 'open-uri'

$companies_ids = {}
$skills_ids = {}
$urls = Set.new
$id = 1
def fetch_and_insert_offers(file)
  doc = Nokogiri::XML(open(file))
  doc.css('item').each do |i|
    offer_id = $id

    url = i.css('link').children.to_s.gsub("'", "''")

    unless $urls.include? url
      $urls.add url

      company = i.css('a10|author a10|name').children.to_s.gsub("'", "''")
      title = i.css('title').children.to_s.gsub("'", "''")
      description = i.css('description').children.to_s.gsub("'", "''")
      pub_date = i.css('pubDate').children.to_s.gsub("'", "''")
      updated = i.css('a10|updated').children.to_s.gsub("'", "''")
      location = i.css('location').children.to_s.gsub("'", "''")

      unless $companies_ids[company]
        puts "INSERT INTO COMPANY(Id, CName) VALUES (#{$id}, '#{company}');" 
        $companies_ids[company] = $id
        $id += 1
      end

      puts "INSERT INTO OFFER(Id, Title, Description, Location, Url, PubDate, Updated, CompanyId) VALUES (#{offer_id}, '#{title}', '#{description}', '#{location}', '#{url}', '#{pub_date}', '#{updated}', #{$companies_ids[company]});"

      puts "INSERT INTO PROPOSES(CompanyId, OfferId) VALUES (#{$companies_ids[company]}, #{offer_id});"

      i.css('category').each do |c|
        sname = c.children.to_s.gsub("'", "''")
        unless $skills_ids[sname]
          puts "INSERT INTO SKILL(Id, SName) VALUES (#{$id}, '#{sname}');"
          $skills_ids[sname] = $id
          $id += 1
          fetch_and_insert_offers "https://careers.stackoverflow.com/jobs/feed?tags=#{sname}"
        end
        puts "INSERT INTO DEMANDS(OfferId, SkillId) VALUES (#{offer_id}, #{$skills_ids[sname]});"
      end

      puts
      $id += 1
    end
  end
end

fetch_and_insert_offers 'https://careers.stackoverflow.com/jobs/feed'

$companies_ids.keys.each do |c|
  begin
    j = JSON.parse open("http://api.glassdoor.com/api/api.htm?t.p=42773&t.k=jd0Cb9TfM3M&format=json&v=1&action=employers&q=#{c}").read
    e = j['response']['employers'].first
    if e
      website = e['website']
      industry = e['industry']
      number_ratings = e['numberOfRatings']
      logo = e['squareLogo']
      overall = e['overallRating']
      culture = e['cultureAndValuesRating']
      leadership = e['seniorLeadershipRating']
      compensation = e['compensationAndBenefitsRating']
      career = e['careerOpportunitiesRating']
      work_life = e['workLifeBalanceRating']
      friends = e['recommendToFriendRating']
      puts "UPDATE COMPANY SET Website = '#{website}', Industry = '#{industry}', NumberOfRatings = #{number_ratings}, Logo = '#{logo}', OverallRating = #{overall}, CultureAndValuesRating = #{culture}, SeniorLeadershipRating = #{leadership}, CompensationAndBenefitsRating = #{compensation}, CareerOpportunitiesRating = #{career}, WorkLifeBalanceRating = #{work_life}, RecomendToFriend = #{friends} WHERE CName = '#{c}';"
      if e['ceo'] && e['ceo']['numberOfRatings'] > 0
        pct = e['ceo']['pctApprove']
        puts "UPDATE COMPANY SET CEOAproval = '#{pct}' WHERE CName = '#{c}';"
      end
    else
      STDERR.puts "Company #{c} not found"
    end
  rescue
    STDERR.puts "ERROR -- #{c}"
  end
  puts
end
