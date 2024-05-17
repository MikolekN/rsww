import os
import unittest

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
#from webdriver_manager.chrome import ChromeDriverManager


class RegisterTests(unittest.TestCase):
    def setUp(self):
        self.BASE_URL = "http://localhost:4200/"
        self.driver = webdriver.Chrome()

    def tearDown(self):
        self.driver.close()

    def test_login_successfull(self):
        self.driver.get(os.path.join(self.BASE_URL, "login"))
        self.driver.find_element(By.XPATH, '/html/body/app-root/div/div/app-login-form/div/div/div/div/div/form/div[1]/input').send_keys("user1")
        self.driver.find_element(By.XPATH, '/html/body/app-root/div/div/app-login-form/div/div/div/div/div/form/div[2]/input').send_keys("password1")
        self.driver.implicitly_wait(5)
        element = self.driver.find_element(By.XPATH, '/html/body/app-root/div/div/app-login-form/div/div/div/div/div/form/div[3]/button')
        self.driver.execute_script("arguments[0].click();", element)
        WebDriverWait(self.driver, 5).until(
            EC.element_to_be_clickable((By.XPATH,
                                        "/html/body/app-root/div/app-header/nav/div/div/a[3]")))

        self.driver.find_element(By.XPATH, "/html/body/div[2]/div/div/mat-snack-bar-container")


    def test_login_unsuccessful(self):
        self.driver.get(os.path.join(self.BASE_URL, "login"))
        self.driver.find_element(By.XPATH,
                                 '/html/body/app-root/div/div/app-login-form/div/div/div/div/div/form/div[1]/input').send_keys(
            "user")
        self.driver.find_element(By.XPATH,
                                 '/html/body/app-root/div/div/app-login-form/div/div/div/div/div/form/div[2]/input').send_keys(
            "password")
        self.driver.implicitly_wait(5)
        element = self.driver.find_element(By.XPATH,
                                           '/html/body/app-root/div/div/app-login-form/div/div/div/div/div/form/div[3]/button')
        self.driver.execute_script("arguments[0].click();", element)
        self.driver.find_element(By.XPATH, "/html/body/div[2]/div/div/mat-snack-bar-container")
        assert self.driver.current_url == "http://localhost:4200/login"

    def test_find_offer(self):
        self.driver.get(os.path.join(self.BASE_URL, "login"))
        self.driver.find_element(By.XPATH,
                                 '/html/body/app-root/div/div/app-login-form/div/div/div/div/div/form/div[1]/input').send_keys(
            "user9")
        self.driver.find_element(By.XPATH,
                                 '/html/body/app-root/div/div/app-login-form/div/div/div/div/div/form/div[2]/input').send_keys(
            "password9")
        login = self.driver.find_element(By.XPATH,
                                           '/html/body/app-root/div/div/app-login-form/div/div/div/div/div/form/div[3]/button')
        self.driver.execute_script("arguments[0].click();", login)

        # user logged in

        WebDriverWait(self.driver, 5).until(
            EC.element_to_be_clickable((By.XPATH, '/html/body/app-root/div/app-header/nav/div/div/a[3]'))).click()
        #self.driver.execute_script("arguments[0].click();", offers)

        self.driver.find_element(By.XPATH, '/html/body/app-root/div/div/app-offers/app-offer-search/form/mat-form-field[1]/div[1]/div[2]/div/input').send_keys('05.08.2024')

        self.driver.find_element(By.XPATH,
                             '/html/body/app-root/div/div/app-offers/app-offer-search/form/mat-form-field[2]/div[1]/div[2]/div/input').send_keys(
        '12.08.2024')

        self.driver.find_element(By.XPATH, '/html/body/app-root/div/div/app-offers/app-offer-search/form/mat-form-field[3]/div[1]/div[2]/div/input').send_keys('2')
        self.driver.find_element(By.XPATH, '/html/body/app-root/div/div/app-offers/app-offer-search/form/mat-form-field[4]/div[1]/div[2]/div/input').send_keys('1')
        self.driver.find_element(By.XPATH, '/html/body/app-root/div/div/app-offers/app-offer-search/form/mat-form-field[5]/div[1]/div[2]/div/input').send_keys('0')

        search = self.driver.find_element(By.XPATH, '/html/body/app-root/div/div/app-offers/app-offer-search/form/button')
        self.driver.implicitly_wait(5)
        self.driver.execute_script("arguments[0].click();", search)
        WebDriverWait(self.driver, 5).until(
            EC.element_to_be_clickable((By.XPATH, "/html/body/app-root/div/div/app-offers/app-offer-search/div[1]/app-single-offer/mat-card/mat-card-content/button")))

        # searched offers

        button = self.driver.find_element(By.XPATH, "/html/body/app-root/div/div/app-offers/app-offer-search/div[1]/app-single-offer/mat-card/mat-card-content/button")

        assert button.text == "Wybieram"

        self.driver.execute_script("arguments[0].click();", button)

        # offer choosed

        WebDriverWait(self.driver, 5).until(
            EC.element_to_be_clickable((By.XPATH,
                                        "/html/body/app-root/div/div/app-selected-offer/mat-card/mat-card-content/select[2]"))).click()
        WebDriverWait(self.driver, 5).until(
            EC.element_to_be_clickable((By.XPATH,
                                        "/html/body/app-root/div/div/app-selected-offer/mat-card/mat-card-content/select[2]/option[1]"))).click()

        WebDriverWait(self.driver, 5).until(
            EC.element_to_be_clickable((By.XPATH,
                                        "/html/body/app-root/div/div/app-selected-offer/mat-card/mat-card-content/button"))).click()

        # offer reserved