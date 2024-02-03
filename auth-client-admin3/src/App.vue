<template>
    <div class="flex h-screen">
        <div class="m-auto">
            <!-- <h1 className="text-3xl font-bold underline">
                Hello world!
            </h1> -->
            <ElCard class=" min-w-[400px]">
                <LoginForm></LoginForm>
            </ElCard>
        </div>
    </div>

</template>

<script setup lang="ts">
import {
    ElCard
} from 'element-plus'

import LoginForm from './components/LoginForm.vue';
</script>


<style scoped>


</style>
