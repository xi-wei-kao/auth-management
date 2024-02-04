import axios from 'axios';
import { LoginActionReq } from './request/requestType';
import qs from 'qs'

const baseUrl = "http://localhost:7100"

const server = axios.create({
    'baseURL': baseUrl,
    // 'withCredentials': true
});

server.interceptors.request.use((config: AxiosRequestConfig) => {
    config.headers['token'] = window.localStorage.getItem("token");

    return config;
}, (error) => {  // 攔截後的請求失敗時，回傳錯誤
    console.log("請求失敗");
    return Promise.reject(error);
});


export default {
    // loginAction: (reqBody: LoginActionReq) => {
    //     return server.post(
    //         `/login`,
    //         reqBody)
    // },
    loginAction: (reqBody: LoginActionReq) => {
        return server.post(
            `/login?${qs.stringify(reqBody)}`)
    },
    queryAllAction: () => {
        return server.get(`/test/getList`)
    },
    logoutAction: () => {
        return server.get(`/logout`)
    },

}