<template>
  <ChooseEdge></ChooseEdge>
</template>

<script>
import ChooseEdge from '../../components/ChooseEdge'

export default {
  name: 'Edge',
  components: {
    ChooseEdge
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
