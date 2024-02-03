import axios from 'axios';
import { LoginActionReq } from './request/requestType';
import qs from 'qs'

const baseUrl = "http://localhost:7100"

const server = axios.create({
    'baseURL': baseUrl,
    // 'withCredentials': true
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
    }
}