<template>
  <div id="app">
    <UserRegister/>
  </div>
</template>

<script>
import UserRegister from '../../components/UserRegister.vue'

export default {
  name: 'Register',
  components: {
    UserRegister
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
