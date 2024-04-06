import axios, { AxiosRequestConfig } from 'axios';
import { GetOverviewReq, LoginActionReq } from './request/requestType';
import qs from 'qs';

import { router } from '../router/index'

const baseUrl = "http://localhost:7100"

const server = axios.create({
    'baseURL': baseUrl,
    // 'withCredentials': true
});

server.interceptors.request.use((config: AxiosRequestConfig) => {
    console.log("interceptor");
    const storeData = localStorage.getItem("myStore");
    let token = "";
    // [Step]: 解析 accessToken
    try { 
        if (storeData) {
            const storeDataJson = JSON.parse(storeData);
            token = storeDataJson['accessToken'];
            config.headers!['Authorization'] = token;
        }
    } catch (error) {
        console.error("localStorage is not a valid JSON! ");
    }

    return config;
}, (error) => {  // 攔截後的請求失敗時，回傳錯誤
    console.log("請求失敗");
    return Promise.reject(error);
});

server.interceptors.response.use(function (response) {

    console.log("response = ", response);
    
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    return response;
  }, function (error) {
    console.log("error.response.status = ", error.response.status);
    if (error.response.status === 401) {
        localStorage.removeItem("myStore");
    }
    router.push({
        'name': 'login'
    });

    return Promise.reject(error);
  });



export default {
    // loginAction: (reqBody: LoginActionReq) => {
    //     return server.post(
    //         `/login`,
    //         reqBody)
    // },
    loginAction: (reqBody: LoginActionReq) => {
        // return server.post(
        //     `/login?`, 
        //     reqBody);
        return server.post(
            `/login?${qs.stringify(reqBody)}`)
    },
    queryAllAction: () => {
        return server.get(`/test/getList`)
    },
    logoutAction: () => {
        return server.get(`/logout`)
    },
    getOverview: (resBody: GetOverviewReq) => {
        return server.post(`/api/dashboard/getOverview`, resBody);
    },


}