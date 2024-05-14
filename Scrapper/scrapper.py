from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.options import Options
from webdriver_manager.chrome import ChromeDriverManager
import json
import os

PRINT_ENABLED = False

chrome_options = Options()
# chrome_options.add_argument("--headless")
chrome_options.add_argument('--ignore-certificate-errors')
chrome_options.add_experimental_option('excludeSwitches', ['enable-logging'])
driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=chrome_options) 

def remove_cookie_popup():
    try:
        popup_button = WebDriverWait(driver, 5).until(EC.element_to_be_clickable((By.XPATH, "/html/body/div[3]/div/div/div/div[2]/button[3]")))
        popup_button.click()
    except:
        pass


def scrape_from_itaka():
    global PRINT_ENABLED

    url = "https://www.itaka.pl/"
    driver.get(url)

    remove_cookie_popup()

    skad_i_jak = WebDriverWait(driver, 5).until(EC.element_to_be_clickable((By.XPATH, "/html/body/div[5]/div[3]/div[1]/div[2]/div[2]/div/div/div[3]")))
    skad_i_jak.click()
    
    wszystkie_lotniska = WebDriverWait(driver, 5).until(EC.element_to_be_clickable((By.XPATH, "/html/body/div[6]/div/div/div/div[1]/div[1]/label")))
    wszystkie_lotniska.click()

    szukaj = WebDriverWait(driver, 5).until(EC.element_to_be_clickable((By.XPATH, "/html/body/div[5]/div[3]/div[1]/div[2]/div[2]/div/div/div[5]/button")))
    szukaj.click()

    sortuj = WebDriverWait(driver, 5).until(EC.element_to_be_clickable((By.XPATH, "/html/body/div[5]/div[3]/div[4]/div[1]/div/div/div/button")))
    sortuj.click()

    dropdown_sortuj = WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.ID, "dropdown-container")))
    najnizsza_cena = WebDriverWait(dropdown_sortuj, 5).until(EC.element_to_be_clickable((By.XPATH, "./div/div/ul/li[3]/button")))
    najnizsza_cena.click()

    offer_urls = []
    for page_num in range(100):
        print(f"Checking page number - {page_num + 1}") if PRINT_ENABLED else None
        try:
            WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.XPATH, "/html/body/div[5]/div[3]/div[4]/div")))
        except:
            break
        
        for i in range(2, 100):
            try:
                offer_xpath = f"/html/body/div[5]/div[3]/div[4]/div[{i}]/div[2]/div/div[3]/div/div[2]/span/span[2]/a"
                WebDriverWait(driver, 1).until(EC.presence_of_element_located((By.XPATH, offer_xpath)))
                offer_link = driver.find_element(By.XPATH, offer_xpath)
                offer_url = offer_link.get_attribute("href")
                offer_urls.append(offer_url)
                print(f"Offer number {i - 1} is saved") if PRINT_ENABLED else None
            except:
                break
        print(f"Finished page number - {page_num + 1}") if PRINT_ENABLED else None

        next_button_xpath = f"/html/body/div[5]/div[3]/div[4]/div[{i}]/div/div[2]/button"
        try:
            next_button = WebDriverWait(driver, 5).until(EC.element_to_be_clickable((By.XPATH, next_button_xpath)))
            next_button.click()
        except:
            print(f"No next page present.") if PRINT_ENABLED else None
            break
    
    for offer_id, offer_url in enumerate(offer_urls):
        try:
            driver.get(offer_url)
            print(f"Offer number {offer_id + 1}:") if PRINT_ENABLED else None

            hotel_name_xpath = "/html/body/div[5]/div[4]/div[2]/div/div[1]/div[3]/div[1]/div[2]/div/span/span[2]/span[1]/span[2]/h1"
            hotel_name_element = WebDriverWait(driver, 5).until(EC.visibility_of_element_located((By.XPATH, hotel_name_xpath)))
            hotel_name = hotel_name_element.text
            print(f"\tHotel name: {hotel_name}") if PRINT_ENABLED else None
            
            hotel_country_xpath = "/html/body/div[5]/div[4]/div[2]/div/div[1]/div[3]/div[1]/div[2]/div/div/div/span/span[2]/div/h4"
            hotel_country_element = WebDriverWait(driver, 5).until(EC.visibility_of_element_located((By.XPATH, hotel_country_xpath)))
            hotel_country = hotel_country_element.text.split(',')[0].strip()
            print(f"\tHotel country: {hotel_country}") if PRINT_ENABLED else None
            
            hotel_room_types_xpath_base = "/html/body/div[5]/div[4]/div[2]/div/div[1]/div[3]/div[1]/div/div/div/section/span/span[2]/div[2]/div/div[{}]/div/div/span/span/h6"
            hotel_room_types = []
            for i in range(1, 3):
                hotel_room_types_xpath = hotel_room_types_xpath_base.format(i)
                try:
                    hotel_room_types_element = driver.find_element(By.XPATH, hotel_room_types_xpath)
                    hotel_room_types.append(hotel_room_types_element.text)
                except:
                    break
            print(f"\tHotel room types:") if PRINT_ENABLED else None
            for hotel_room_type in hotel_room_types:
                print(f"\t\t{hotel_room_type}") if PRINT_ENABLED else None

            rozklad_lotu_xpath = "/html/body/div[5]/div[4]/div[2]/div/div[1]/div[3]/div[2]/div/div/div/div[4]/div[2]/div/button"
            rozklad_lotu_element = WebDriverWait(driver, 5).until(EC.element_to_be_clickable((By.XPATH, rozklad_lotu_xpath)))
            rozklad_lotu_element.click()

            flight_to_departure_place_xpath = "/html/body/div/div/div/div/div[2]/div[1]/section/div[1]/ul/li[1]/ul/li[1]/div[2]/span/span[2]/span/div[2]"
            flight_to_departure_place_element = WebDriverWait(driver, 5).until(EC.visibility_of_element_located((By.XPATH, flight_to_departure_place_xpath)))
            flight_to_departure_place = flight_to_departure_place_element.text

            flight_to_arrival_place_xpath_2 = "/html/body/div/div/div/div/div[2]/div[1]/section/div[1]/ul/li[2]/ul/li/div[2]/span/span[2]/span/div[2]"
            flight_to_arrival_place_xpath_3 = "/html/body/div/div/div/div/div[2]/div[1]/section/div[1]/ul/li[3]/ul/li/div[2]/span/span[2]/span/div[2]"
            try:
                flight_to_arrival_place_element = driver.find_element(By.XPATH, flight_to_arrival_place_xpath_3)
            except:
                try:
                    flight_to_arrival_place_element = driver.find_element(By.XPATH, flight_to_arrival_place_xpath_2)
                except:
                    continue
            flight_to_arrival_place = flight_to_arrival_place_element.text

            # not sure because in flight with connecting flights i have to save both flights or decide which code to choose
            flight_to_code_xpath = "/html/body/div/div/div/div/div[2]/div[1]/section/div[1]/ul/li[1]/ul/li[2]/span[2]/span/span/span[2]/span"
            flight_to_code_element = WebDriverWait(driver, 5).until(EC.visibility_of_element_located((By.XPATH, flight_to_code_xpath)))
            flight_to_code = flight_to_code_element.text

            flight_to_time_start_xpath = "/html/body/div/div/div/div/div[2]/div[1]/section/div[1]/ul/li[1]/ul/li[1]/div[1]/div/span/span[2]/div"
            flight_to_time_start_element = WebDriverWait(driver, 5).until(EC.visibility_of_element_located((By.XPATH, flight_to_time_start_xpath)))
            flight_to_time_start = flight_to_time_start_element.text
            flight_to_time_start_hour, flight_to_time_start_minute = map(int, flight_to_time_start.split(':'))
            flight_to_time_start_total_minutes = flight_to_time_start_hour * 60 + flight_to_time_start_minute

            flight_to_time_end_xpath_2 = "/html/body/div/div/div/div/div[2]/div[1]/section/div[1]/ul/li[2]/ul/li/div[1]/div/span/span[2]/div"
            flight_to_time_end_xpath_3 = "/html/body/div/div/div/div/div[2]/div[1]/section/div[1]/ul/li[3]/ul/li/div[1]/div/span/span[2]/div"
            try:
                flight_to_time_end_element = driver.find_element(By.XPATH, flight_to_time_end_xpath_3)
            except:
                try:
                    flight_to_time_end_element = driver.find_element(By.XPATH, flight_to_time_end_xpath_2)
                except:
                    continue
            flight_to_time_end = flight_to_time_end_element.text
            flight_to_time_end_hour, flight_to_time_end_minute = map(int, flight_to_time_end.split(':'))
            flight_to_time_end_total_minutes = flight_to_time_end_hour * 60 + flight_to_time_end_minute
            
            length_of_to_flight = 1
            if flight_to_time_end_total_minutes >= flight_to_time_start_total_minutes:
                length_of_to_flight = flight_to_time_end_total_minutes - flight_to_time_start_total_minutes
            else:
                length_of_to_flight = (24 * 60 - flight_to_time_start_total_minutes) + flight_to_time_end_total_minutes

            print(f"\tStart departure airport: {flight_to_departure_place}") if PRINT_ENABLED else None
            print(f"\tStart arrival airport: {flight_to_arrival_place}") if PRINT_ENABLED else None
            print(f"\tStart flight code: {flight_to_code}") if PRINT_ENABLED else None
            print(f"\tStart flight length (mins): {length_of_to_flight}") if PRINT_ENABLED else None

            flight_from_departure_place_xpath = "/html/body/div/div/div/div/div[2]/div[2]/section/div[1]/ul/li[1]/ul/li[1]/div[2]/span/span[2]/span/div[2]"
            flight_from_departure_place_element = WebDriverWait(driver, 5).until(EC.visibility_of_element_located((By.XPATH, flight_from_departure_place_xpath)))
            flight_from_departure_place = flight_from_departure_place_element.text

            flight_from_arrival_place_xpath_2 = "/html/body/div/div/div/div/div[2]/div[2]/section/div[1]/ul/li[2]/ul/li/div[2]/span/span[2]/span/div[2]"
            flight_from_arrival_place_xpath_3 = "/html/body/div/div/div/div/div[2]/div[2]/section/div[1]/ul/li[3]/ul/li/div[2]/span/span[2]/span/div[2]"
            try:
                flight_from_arrival_place_element = driver.find_element(By.XPATH, flight_from_arrival_place_xpath_3)
            except:
                try:
                    flight_from_arrival_place_element = driver.find_element(By.XPATH, flight_from_arrival_place_xpath_2)
                except:
                    continue
            flight_from_arrival_place = flight_from_arrival_place_element.text

            # not sure because in flight with connecting flights i have to save both flights or decide which code to choose
            flight_from_code_xpath = "/html/body/div/div/div/div/div[2]/div[2]/section/div[1]/ul/li[1]/ul/li[2]/span[2]/span/span/span[2]/span"
            flight_from_code_element = WebDriverWait(driver, 5).until(EC.visibility_of_element_located((By.XPATH, flight_from_code_xpath)))
            flight_from_code = flight_from_code_element.text

            flight_from_time_start_xpath = "/html/body/div/div/div/div/div[2]/div[2]/section/div[1]/ul/li[1]/ul/li[1]/div[1]/div/span/span[2]/div"
            flight_from_time_start_element = WebDriverWait(driver, 5).until(EC.visibility_of_element_located((By.XPATH, flight_from_time_start_xpath)))
            flight_from_time_start = flight_from_time_start_element.text
            flight_from_time_start_hour, flight_from_time_start_minute = map(int, flight_from_time_start.split(':'))
            flight_from_time_start_total_minutes = flight_from_time_start_hour * 60 + flight_from_time_start_minute

            flight_from_time_end_xpath_2 = "/html/body/div/div/div/div/div[2]/div[2]/section/div[1]/ul/li[2]/ul/li/div[1]/div/span/span[2]/div"
            flight_from_time_end_xpath_3 = "/html/body/div/div/div/div/div[2]/div[2]/section/div[1]/ul/li[3]/ul/li/div[1]/div/span/span[2]/div"
            try:
                flight_from_time_end_element = driver.find_element(By.XPATH, flight_from_time_end_xpath_3)
            except:
                try:
                    flight_from_time_end_element = driver.find_element(By.XPATH, flight_from_time_end_xpath_2)
                except:
                    continue
            flight_from_time_end = flight_from_time_end_element.text
            flight_from_time_end_hour, flight_from_time_end_minute = map(int, flight_from_time_end.split(':'))
            flight_from_time_end_total_minutes = flight_from_time_end_hour * 60 + flight_from_time_end_minute
            
            if flight_from_time_end_total_minutes >= flight_from_time_start_total_minutes:
                length_of_from_flight = flight_from_time_end_total_minutes - flight_from_time_start_total_minutes
            else:
                length_of_from_flight = (24 * 60 - flight_from_time_start_total_minutes) + flight_from_time_end_total_minutes

            print(f"\tEnd departure airport: {flight_from_departure_place}") if PRINT_ENABLED else None
            print(f"\tEnd arrival airport: {flight_from_arrival_place}") if PRINT_ENABLED else None
            print(f"\tEnd flight code: {flight_from_code}") if PRINT_ENABLED else None
            print(f"\tEnd flight length (mins): {length_of_from_flight}") if PRINT_ENABLED else None

            offer_code_xpath = "/html/body/div[5]/div[4]/div[2]/div/div[1]/div[3]/div[1]/p"
            offer_code_element = WebDriverWait(driver, 5).until(EC.visibility_of_element_located((By.XPATH, offer_code_xpath)))
            offer_code = offer_code_element.text
            offer_code = offer_code.replace('Kod oferty: ', '')
            print(f"\tOffer code: {offer_code}") if PRINT_ENABLED else None
            offer = {
                "offer_code": str(offer_code),
                "hotel_name": str(hotel_name),
                "hotel_country": str(hotel_country),
                "hotel_room_types": hotel_room_types,
                "start_departure_airport_name": str(flight_to_departure_place),
                "start_arrival_airport_name": str(flight_to_arrival_place),
                "start_flight_code": str(flight_to_code),
                "start_flight_length": str(length_of_to_flight),
                "end_departure_airport_name": str(flight_from_departure_place),
                "end_arrival_airport_name": str(flight_from_arrival_place),
                "end_flight_code": str(flight_from_code),
                "end_flight_length": str(length_of_from_flight),
            }
            offers = []
            if not os.path.isfile('offers.json'):
                offers.append(offer)
                with open('offers.json', "w") as f:
                    f.write(json.dumps(offers, indent=2))
            else:
                with open('offers.json', "r") as f:
                    offers = json.load(f)
                offers.append(offer)
                with open('offers.json', "w") as f:
                    f.write(json.dumps(offers, indent=2))
        except:
            pass

def main():
    scrape_from_itaka()
    driver.quit()

if __name__ == "__main__":
    main()
