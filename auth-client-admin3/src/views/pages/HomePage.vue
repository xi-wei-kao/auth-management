<template>
    <div class="flex flex-col mx-40 gap-3 justify-center mt-5">
        <h1 className="text-3xl font-bold underline text-center">
            Hello world!
        </h1>
        <ElButton :type="'primary'" @click="handleQuery">測試查詢</ElButton>
        {{ resData }}
        {{   }}
        <ElButton :type="'primary'" @click="handleGetOverview">測試Dashboard查詢</ElButton>
        <ElButton :type="'primary'" @click="handleStore">測試store</ElButton>
        <ElButton :type="'danger'" @click="handleLogout">登出</ElButton>
    </div>
</template>
<script setup lang="ts">
import {
    ElCard,
    ElButton,
    ElMessage
} from 'element-plus'
import service from '@/service/index'
import { ref } from 'vue';
import { router } from '@/router';
import { GetOverviewReq } from '@/service/request/requestType';

import { myStore } from '../../store/index';
const store = myStore();

const resData = ref();
const logoutData = ref();
const handleQuery = async () => {
    const response = await service.queryAllAction();
    resData.value = response.data;
}

const handleGetOverview = async () => {
    const reqBody: GetOverviewReq = {
        "type": "day"
    }
    
    const response = await service.getOverview(reqBody);
    console.log("getOverview() response = ", response);
    resData.value = response.data;

    ElMessage({
        'type': 'success',
        'message': response.data.message
    });


}

const handleLogout = async () => {
    const response = await service.logoutAction();
    logoutData.value = response.data;
    ElMessage({
        'type': 'success',
        'message': response.data.message
    });

    store.$patch({
        'accessToken': ""
    });

    router.push({
        'name': 'login'
    });
}

const handleStore = () => {
    store.$patch({
        'test1': '123'
    })
}

</script>
<style lang="scss">
    
</style>