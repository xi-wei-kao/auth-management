import { defineStore } from "pinia";

export const myStore = defineStore('myStore', {
    'state': () => {
        return {
            'accessToken': "",
            'test1': ""
        }
    },
    'persist': true
})