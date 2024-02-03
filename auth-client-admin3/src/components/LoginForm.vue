<template>
    <div>
        <ElForm>
            <ElFormItem>
                <div class="w-full">
                    <h3 class="">帳號：</h3>
                    <ElInput v-model="loginModel.username"></ElInput>
                </div>
            </ElFormItem>
            <ElFormItem>
                <div class="w-full">
                    <h3 class="">密碼：</h3>
                    <ElInput v-model="loginModel.password"></ElInput>
                </div>
            </ElFormItem>
            <ElButton :type="'primary'"
                @click.prevent="handleLogin">登入
            </ElButton>
        </ElForm>
    </div>
</template>
<script setup lang="ts">
import {
    ElButton,
    ElForm,
    ElFormItem,
    ElInput,
    ElMessage
} from 'element-plus'
import { ref } from 'vue';

import actions from '../service/index';
import {
    LoginActionReq
} from '../service/request/requestType';

const loginModel = ref<LoginActionReq>({
    username: "",
    password: ""
});

const handleLogin = async () => {
    try {
        const result = await actions.loginAction(loginModel.value);
        const data = result.data;
        console.log("data = ", data);
        if (data.code === "200") {
            ElMessage({
                'type': 'success',
                'message': data.message
            });
        } else {
            ElMessage({
                'type': 'error',
                'message': data.message
            });
        }
    } catch (error) {
        console.error("error = ", error);
        
    }
}

</script>
<style lang="scss">
    
</style>