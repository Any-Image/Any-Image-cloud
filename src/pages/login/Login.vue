<template>
  <UserLogin></UserLogin>
</template>

<script>
import UserLogin from '../../components/UserLogin.vue'

export default {
  name: 'Login',
  components: {
    UserLogin
  }
}
</script>

<style>
*{
  -moz-user-select: none;
  -webkit-user-select: none;
  user-select: none;
  -o-user-select: none;
  -ms-user-select: none;
}
</style>
