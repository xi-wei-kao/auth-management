<template>
    <div class="flex flex-col mx-40 gap-3 justify-center mt-5">
        <h1 className="text-3xl font-bold underline text-center">
            Hello world!
        </h1>
        <ElButton :type="'primary'" @click="handleQuery">測試查詢</ElButton>
        {{ resData }}
        <ElButton :type="'danger'">登出</ElButton>
    </div>
</template>
<script setup lang="ts">
import {
    ElCard,
    ElButton
} from 'element-plus'
import service from '@/service/index'
import { ref } from 'vue';

const resData = ref();
const handleQuery = async () => {
    const response = await service.queryAllAction();
    resData.value = response.data;
}

</script>
<style lang="scss">
    
</style>