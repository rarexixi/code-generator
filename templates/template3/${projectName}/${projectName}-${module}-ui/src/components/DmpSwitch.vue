<template>
    <a-switch v-model:checked="checked" @change="emitValue" />
</template>

<script lang="ts">

import { defineComponent, ref, toRefs, watch } from 'vue';

export default defineComponent({
    name: 'DmpSwitch',
    props: {
        value: {
            type: [Number, String, Boolean],
            required: false,
            default: () => false
        },
        activeValue: {
            type: [Number, String, Boolean],
            required: false,
            default: () => true
        },
        inactiveValue: {
            type: [Number, String, Boolean],
            required: false,
            default: () => false
        }
    },
    emits: ['update:value', 'change'],
    setup(props, { attrs, slots, emit }) {
        const { value, activeValue, inactiveValue } = toRefs(props);
        const checked = ref(value.value === activeValue.value)
        watch(value, (newVal) => {
            checked.value = newVal === activeValue.value
        })
        const emitValue = (newValue: boolean) => {
            emit('update:value', newValue ? activeValue : inactiveValue)
            emit('change', newValue ? activeValue : inactiveValue)
        }
        return {
            checked,
            emitValue
        }
    }
})
</script>