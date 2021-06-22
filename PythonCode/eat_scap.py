#https://www.eat.ma/
#cities, Restaurants list, Informations, menu
from selenium import webdriver                    
from selenium.webdriver.common.keys import Keys
from webdriver_manager.chrome import ChromeDriverManager
from selenium.common.exceptions import NoSuchElementException
from selenium.common.exceptions import ElementNotInteractableException
from selenium.common.exceptions import ElementClickInterceptedException
from selenium.webdriver.chrome.options import Options
import time  
import os
import requests
import shutil
import json

data = {}
data['Restaurants'] = []
json_restaurant_name = ''
json_restaurant_tags = []
json_restaurant_services = []
json_restaurant_location = ''
json_restaurant_city = ''
json_restaurant_verified = ''
json_restaurant_phone_number = ''
json_restaurant_main_picture_path = ''
json_restaurant_menu_pictures_path = []
json_restaurant_review = ''
json_restaurant_review_count = ''

URL = 'https://www.eat.ma/ville'
chrome_options = Options()
chrome_options.add_argument("--window-size=920,1080")
driver = webdriver.Chrome(ChromeDriverManager().install(), chrome_options=chrome_options)
driver.get(URL)
time.sleep(2)
cities = []

for x in range(1,10):
    for y in range(1,10):

        try:
            city = driver.find_element_by_xpath("/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section["+str(x)+"]/div/div/div["+str(y)+"]/div/div/div/div/h3")
            print("#City --- "+city.text)
            cities.append(city.text)
        except NoSuchElementException:
            pass

time.sleep(1)
if not os.path.exists('EatMA'):
    os.makedirs('EatMA')
for city in cities:
    json_restaurant_city = city
    driver.get(URL+"/"+city.lower().replace(" ", "").replace("é", "e").replace("è", "e"))
    time.sleep(2)
    
    for x in range(1, 20):
        try:
            driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
            time.sleep(0.5)
            load_more = driver.find_element_by_class_name("facetwp-load-more")
            load_more.click()
            time.sleep(5)
        except ElementClickInterceptedException:
            pass
        except NoSuchElementException:
            pass
        except ElementNotInteractableException:
            pass
    
    restaurants = driver.find_elements_by_tag_name('article')
    restaurants_url = []
    for restaurant in restaurants:
        restaurant_url = restaurant.find_element_by_xpath("./div/div/div/section/div/div/div[2]/div/div/div[1]/div/h2/a").get_attribute('href')
        print("Restaurant --- "+restaurant_url)
        restaurants_url.append(restaurant_url)
    
    for restaurant_url in restaurants_url:
        json_restaurant_name = ''
        json_restaurant_tags = []
        json_restaurant_services = []
        json_restaurant_location = ''
        json_restaurant_city = ''
        json_restaurant_verified = ''
        json_restaurant_phone_number = ''
        json_restaurant_main_picture_path = ''
        json_restaurant_menu_pictures_path = []
        json_restaurant_review = ''
        json_restaurant_review_count = ''
        try:
            driver.get(restaurant_url)
            time.sleep(2)
            parent_div = driver.find_element_by_xpath("/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section[1]/div/div/div[2]/div/div")
            count_of_divs = len(parent_div.find_elements_by_xpath("./div"))
            if not os.path.exists('EatMA/'+restaurant_url.split("/")[-1].replace("-", "_")):
                os.makedirs('EatMA/'+restaurant_url.split("/")[-1].replace("-", "_"))
            try:
                #scroll to maps view
                location_view = driver.find_element_by_xpath('/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section[4]/div/div/div/div/div/div[1]/div/ul/li/span[2]')
                driver.execute_script("arguments[0].scrollIntoView();", location_view)
                time.sleep(1)
            except NoSuchElementException:
                    print("Restaurant Location Div --- Not Found")
                    pass

            try:
                restaurant_picture_url = driver.find_element_by_xpath("/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section[1]/div/div/div[1]/div/div/div/div/div/img").get_attribute("src").replace("-300x300", "")
                restaurant_picture_name = restaurant_picture_url.split("/")[-1]

                r = requests.get(restaurant_picture_url, stream = True)
                if r.status_code == 200:
                    r.raw.decode_content = True
                    with open('EatMA/'+restaurant_url.split("/")[-1].replace("-", "_")+'/'+restaurant_picture_name,'wb') as f:
                        shutil.copyfileobj(r.raw, f)
                    
                    print('Image sucessfully Downloaded: ',restaurant_picture_name)
                    json_restaurant_main_picture_path = 'EatMA/'+restaurant_url.split("/")[-1].replace("-", "_")+'/'+restaurant_picture_name
                else:
                    print('Image Couldn\'t be retreived')
            except NoSuchElementException:
                    print("Restaurant Main Picture --- Not Found")
                    pass

            if(count_of_divs == 8):
                #res name 
                restaurant_name = driver.find_element_by_xpath("/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section[1]/div/div/div[2]/div/div/div[1]/div/h1").text
                print("Restaurant Name --- "+restaurant_name)
                json_restaurant_name = restaurant_name
                #verified?
                print("Restaurant --- Unverified")
                json_restaurant_verified = 'No'
                #tags
                restaurant_tags = driver.find_element_by_xpath("/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section[1]/div/div/div[2]/div/div/div[2]/div/div").text
                print("Restaurant Tags --- "+restaurant_tags)
                json_restaurant_tags = restaurant_tags.split(", ")
                #services
                restaurant_services = driver.find_element_by_xpath("/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section[1]/div/div/div[2]/div/div/div[4]/div/div").text
                print("Restaurant Services --- "+restaurant_services)
                json_restaurant_services = restaurant_services.split(", ")
                #location
                restaurant_location = driver.find_element_by_xpath("/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section[1]/div/div/div[2]/div/div/div[5]/div/div").text
                print("Restaurant Location --- "+restaurant_location)
                json_restaurant_location = restaurant_location
                #phone
                #/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section[1]/div/div/div[2]/div/div/div[9]/div/ul/li/a/span[2]
                try:
                    restaurant_phone = driver.find_element_by_xpath("/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section[1]/div/div/div[2]/div/div/div[8]/div/ul/li/a/span[2]").text
                    print("Restaurant Phone --- "+restaurant_phone)
                    json_restaurant_phone_number = restaurant_phone.replace("+212", "0").replace("-", "").replace(" ", "")
                except NoSuchElementException:
                    print("Restaurant Phone --- Not Found")
                    pass
            if(count_of_divs == 9):
                #res name 
                restaurant_name = driver.find_element_by_xpath("/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section[1]/div/div/div[2]/div/div/div[1]/div/h1").text
                print("Restaurant Name --- "+restaurant_name)
                json_restaurant_name = restaurant_name
                #verified?
                restaurant_tags = driver.find_element_by_xpath("/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section[1]/div/div/div[2]/div/div/div[2]/div/div").text
                print("Restaurant --- Verified")
                json_restaurant_verified = 'Yes'
                #tags
                restaurant_tags = driver.find_element_by_xpath("/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section[1]/div/div/div[2]/div/div/div[3]/div/div").text
                print("Restaurant Tags --- "+restaurant_tags)
                json_restaurant_tags = restaurant_tags.split(", ")
                #services
                restaurant_services = driver.find_element_by_xpath("/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section[1]/div/div/div[2]/div/div/div[5]/div/div").text
                print("Restaurant Services --- "+restaurant_services)
                json_restaurant_services = restaurant_services.split(", ")
                #location
                restaurant_location = driver.find_element_by_xpath("/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section[1]/div/div/div[2]/div/div/div[6]/div/div").text
                print("Restaurant Location --- "+restaurant_location)
                json_restaurant_location = restaurant_location
                #phone
                #/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section[1]/div/div/div[2]/div/div/div[9]/div/ul/li/a/span[2]
                try:
                    restaurant_phone = driver.find_element_by_xpath("/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section[1]/div/div/div[2]/div/div/div[9]/div/ul/li/a/span[2]").text
                    print("Restaurant Phone --- "+restaurant_phone)
                    json_restaurant_phone_number = restaurant_phone.replace("+212", "0").replace("-", "").replace(" ", "")
                except NoSuchElementException:
                    print("Restaurant Phone --- Not Found")
                    pass
            
            #menu data

            if not os.path.exists('EatMA/'+restaurant_url.split("/")[-1].replace("-", "_")+'/Menu'):
                os.makedirs('EatMA/'+restaurant_url.split("/")[-1].replace("-", "_")+'/Menu')
            figures = driver.find_elements_by_xpath('//*[@id="gallery-1"]/figure')
            json_restaurant_menu_pictures_path = []
            for figure in figures:
                menu_img_url = figure.find_element_by_xpath('./div/a/img').get_attribute("src").replace("-150x150", "")
                menu_picture_name = menu_img_url.split("/")[-1]

                r = requests.get(menu_img_url, stream = True)
                if r.status_code == 200:
                    r.raw.decode_content = True
                    with open('EatMA/'+restaurant_url.split("/")[-1].replace("-", "_")+'/Menu/'+menu_picture_name,'wb') as f:
                        shutil.copyfileobj(r.raw, f)
                    
                    print('Image sucessfully Downloaded: ',menu_picture_name)
                    json_restaurant_menu_pictures_path.append('EatMA/'+restaurant_url.split("/")[-1].replace("-", "_")+'/Menu/'+menu_picture_name)
                else:
                    print('Image Couldn\'t be retreived')
            
            try:
                #maps data
                #driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
                #time.sleep(1)
                iframe_maps = driver.find_element_by_xpath('/html/body/div[2]/div/div/section[2]/div/div/div/div/div/section[4]/div/div/div/div/div/div[2]/div/div/iframe')
                driver.execute_script("arguments[0].scrollIntoView();", iframe_maps)
                time.sleep(0.5)
                driver.switch_to.frame(iframe_maps)
                time.sleep(1)
                
                #reveiw
                restaurant_review = driver.find_element_by_xpath('//*[@id="mapDiv"]/div[1]/div/div[4]/div/div/div/div/div[5]/div[1]').text
                print("Restaurant Review --- "+restaurant_review)
                json_restaurant_review = restaurant_review
                #count
                restaurant_review_count = driver.find_element_by_xpath('//*[@id="mapDiv"]/div[1]/div/div[4]/div/div/div/div/div[5]/a').text
                print("Restaurant Review Count --- "+restaurant_review_count)
                json_restaurant_review_count = json_restaurant_review_count.replace(" reviews","")
                driver.switch_to.default_content()
                time.sleep(1)
            except NoSuchElementException:
                    print("Restaurant Maps --- Not Found")
                    pass
            
            data['Restaurants'].append({
                'name': json_restaurant_name,
                'tags': json_restaurant_tags,
                'services': json_restaurant_services,
                'verified': json_restaurant_verified,
                'location': json_restaurant_location,
                'city': json_restaurant_city,
                'phone number': json_restaurant_phone_number,
                'main picture': json_restaurant_main_picture_path,
                'menu pictures': json_restaurant_menu_pictures_path,
                'review': json_restaurant_review,
                'review count': json_restaurant_review_count})

        except NoSuchElementException:
                pass
with open('data.txt', 'w') as outfile:
    json.dump(data, outfile)    

        

            