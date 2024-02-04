<template>
    <div class="flex flex-col mx-40 gap-3 justify-center mt-5">
        <h1 className="text-3xl font-bold underline text-center">
            Hello world!
        </h1>
        <ElButton :type="'primary'" @click="handleQuery">測試查詢</ElButton>
        {{ resData }}
        {{   }}
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

const resData = ref();
const logoutData = ref();
const handleQuery = async () => {
    const response = await service.queryAllAction();
    resData.value = response.data;
}

const handleLogout = async () => {
    const response = await service.logoutAction();
    logoutData.value = response.data;
    ElMessage({
        'type': 'success',
        'message': response.data.message
    });
    window.localStorage.removeItem("token");
    router.push({
        'name': 'login'
    });
}

</script>
<style lang="scss">
    
</style>