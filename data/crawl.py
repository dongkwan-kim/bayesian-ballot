from selenium import webdriver
from time import sleep
import re
import csv
from typing import TextIO
import inspect


BILLS = 1521
PATH = '/Users/dongkwan/chromedriver'
BASE_URL = 'http://likms.assembly.go.kr/bill/billVoteResult.do'


def run_driver(path: str) -> webdriver.Chrome:
    driver = webdriver.Chrome(path)
    driver.implicitly_wait(3)
    return driver


def get_members(driver: webdriver.Chrome) -> list:
    url = 'http://likms.assembly.go.kr/bill/memVoteResult.do'
    driver.get(url)
    sleep(2)
    members = driver.find_elements_by_tag_name('tbody')[0].text.split()
    print('Complete: {}'.format(inspect.stack()[0][3]))
    return members


def get_js_to_result(driver: webdriver.Chrome) -> dict:
    url = '{0}#20_360_3_20180521______{1}_1'.format(BASE_URL, BILLS)
    driver.get(url)
    sleep(4)

    js_dict = {}
    for anchor in driver.find_elements_by_css_selector('.alignL > a'):
        js = anchor.get_attribute('href').replace('javascript:', '')
        matched = re.search("fnViewBillDetail\('\d+','\d+','.+','(\d+)','.+','.+','.+','\d+'\)", js).groups()
        bill_no = matched[0]
        js_dict[bill_no] = js

    print('Complete: {}'.format(inspect.stack()[0][3]))
    return js_dict


def get_vote_row(members: list, pro: list, con: list, wdr: list) -> dict:
    vote_row_dict = {}
    for m in members:
        if m in pro:
            vote_row_dict[m] = 'pro'
        elif m in con:
            vote_row_dict[m] = 'con'
        elif m in wdr:
            vote_row_dict[m] = 'wdr'
        else:
            vote_row_dict[m] = 'abs'
    return vote_row_dict


def get_vote_dict(driver: webdriver.Chrome, js_dict: dict, members: list, check_interval: int=10) -> dict:

    def get_arr(s):
        if '없습니다' in s:
            return []
        else:
            return s.split()

    driver.get(BASE_URL)
    sleep(1)

    vote_dict = {}

    for i, (bill_no, js) in enumerate(js_dict.items()):

        driver.execute_script(js)
        sleep(2)

        pro, con, wdr, _ = [get_arr(x.text) for x in driver.find_elements_by_tag_name('tbody')]

        vote_dict[bill_no] = get_vote_row(members, pro, con, wdr)
        driver.back()
        sleep(1)

        if i % check_interval == 0:
            print('Complete: {} of {}'.format(i, inspect.stack()[0][3]))

    print('Complete: {}'.format(inspect.stack()[0][3]))
    return vote_dict


def writer_csv(filename, fieldnames) -> (TextIO, csv.DictWriter):
    f = open(filename, "w", encoding="utf-8")
    wtr = csv.DictWriter(f, fieldnames=fieldnames)
    wtr.writeheader()
    return f, wtr


def run():
    driver = run_driver(PATH)

    members = sorted(get_members(driver))
    js_dict = get_js_to_result(driver)

    vote_dict = get_vote_dict(driver, js_dict, members)

    field_names = ['bill_no'] + members
    f, wtr = writer_csv('vote_result_{0}.csv'.format(BILLS), fieldnames=field_names)

    for bill_no, vote_row in vote_dict.items():
        vote_row['bill_no'] = bill_no
        wtr.writerow(vote_row)

    f.close()
    print('Complete: {}'.format(inspect.stack()[0][3]))


if __name__ == '__main__':
    run()
