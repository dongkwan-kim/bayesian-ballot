#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import re
import sys
import time
import asyncio
import aiohttp
import uvloop
asyncio.set_event_loop_policy(uvloop.EventLoopPolicy())


async def get_party(session, sema, number):
    url = 'http://likms.assembly.go.kr/bill/memVoteDetail.do'
    dat = f'ageFrom=20&ageTo=20&age=20&sessionCd=&currentsCd=&currentsDt=&orderColumn=ProposeDt&orderType=ASC&lastQuery=&strPage=1&pageSize=10&maxPage=10&ageCheck=&tabMenuType=billVoteResult&searchYn=%EC%A0%84%EC%B2%B4&picDeptCd={number}&polyNm=&polyCd=&searchEmpNm=&srchHangul='
    headers = {'Referer': 'http://likms.assembly.go.kr/bill/memVoteResult.do',
               'Content-Type': 'application/x-www-form-urlencoded'}
    async with sema, session.post(url, data=dat, headers=headers) as resp:
        resp = await resp.text()
        try:
            resp = str(resp)
            resp = resp.split(r'<dt>정당</dt>')[1]
            resp = resp.split(r'</dd>')[0]
            resp = resp.split(r'<dd>')[1].strip()
            sys.stdout.buffer.write(resp.encode('utf-8'));
            sys.stdout.buffer.write('\n'.encode('utf-8'));
            return resp
        except:
            print(f'[{time.ctime()}] Failed Retrieving {number}')
            return '???'


async def get_list(session, sema):
    url = 'http://likms.assembly.go.kr/bill/memVoteResult.do'
    pattern = re.compile(r"javascript:fnViewMemDetail[(]'([0-9]+)'[)]" +\
                         r'"[ \t]+title=[^>]+[>]([^<]+)[<]', re.MULTILINE)
    async with sema, session.get(url) as resp:
        resp = await resp.text()
        try:
            resp = str(resp)
            lst = [(x.strip(), y.strip()) for x, y in pattern.findall(resp)]
            return lst
        except:
            print(f'[{time.ctime()}] Failed Retrieving List')
            return []


async def main():
    sema = asyncio.BoundedSemaphore(8)
    headers = {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36'}
    async with aiohttp.ClientSession(headers=headers) as session:
        s = sorted(await get_list(session, sema), key=lambda x:x[1])
        ret = await asyncio.gather(*[
                asyncio.ensure_future(get_party(session, sema, x)) for x, _ in s])
    with open('party.csv', 'w') as f:
        f.buffer.write(f'name,party\n'.encode('utf-8'))
        for i, j in zip(s, ret):
            f.buffer.write(f'{i[1]},{j}\n'.encode('utf-8'))

loop = asyncio.get_event_loop()
loop.run_until_complete(main())
loop.close()
