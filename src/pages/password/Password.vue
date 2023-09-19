<template>
  <div id="app">
    <FindPass/>
  </div>
</template>

<script>
import FindPass from '../../components/FindPass.vue'

export default {
  name: 'Password',
  components: {
    FindPass
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