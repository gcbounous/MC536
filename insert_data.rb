require 'nokogiri'
require 'json'
require 'set'
require 'open-uri'

companies = Set.new

companies_ids = {}
offers_ids = {}
skills_ids = {}

doc = Nokogiri::XML(File.open('so.rss'))

id = 1
doc.css('item').each do |i|
  offer_id = id

  # There is too much repetition here...
  url = i.css('link').children.to_s.sub("'", "''")
  company = i.css('a10|author a10|name').children.to_s.sub("'", "''")
  title = i.css('title').children.to_s.sub("'", "''")
  description = i.css('description').children.to_s.sub("'", "''")
  pub_date = i.css('pubDate').children.to_s.sub("'", "''")
  updated = i.css('a10|updated').children.to_s.sub("'", "''")
  location = i.css('location').children.to_s.sub("'", "''")

  unless companies_ids[company]
    puts "INSERT INTO COMPANY(Id, CName) VALUES (#{id}, '#{company}');" 
    companies_ids[company] = id
    id += 1
  end

  puts "INSERT INTO OFFER(Id, Title, Description, Location, Url, PubDate, Updated, CompanyId) VALUES (#{offer_id}, '#{title}', '#{description}', '#{location}', '#{url}', '#{pub_date}', '#{updated}', #{companies_ids[company]});"

  puts "INSERT INTO PROPOSES(CompanyId, OfferId) VALUES (#{companies_ids[company]}, #{offer_id});"

  i.css('category').each do |c|
    sname = c.children.to_s.sub("'", "''")
    unless skills_ids[sname]
      puts "INSERT INTO SKILL(Id, SName) VALUES (#{id}, '#{sname}');"
      skills_ids[sname] = id
      id += 1
    end
    puts "INSERT INTO DEMANDS(OfferId, SkillId) VALUES (#{offer_id}, #{skills_ids[sname]});"
  end

  puts
  id += 1
end

# companies.each do |c|
#   j = JSON.parse open("http://api.glassdoor.com/api/api.htm?t.p=42773&t.k=jd0Cb9TfM3M&format=json&v=1&action=employers&q=#{c}").read
#   e = j['response']['employers'].first
#   if e
#     puts e['name']
#     puts e['website']
#     puts e['industry']
#     puts e['numberOfRatings']
#     puts e['squareLogo']
#     puts e['overallRating']
#     puts e['cultureAndValuesRating']
#     puts e['seniorLeadershipRating']
#     puts e['compensationAndBenefitsRating']
#     puts e['workLifeBalanceRating']
#     puts e['recommendToFriendRating']
#     puts e['ceo']['pctApprove'] if e['ceo']
#   else
#     puts "Company #{c} not found."
#   end
#   puts
# end
