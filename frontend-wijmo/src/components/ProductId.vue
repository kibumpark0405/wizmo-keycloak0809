<template>
    <div>
        <BasePicker v-if="editMode" searchApiPath="/search/findByCompanyQuery" searchParameterName="name" idField="code" nameField="name" path="" label="ProductId" v-model="value" @selected="pick" :editMode="editMode" />
        <div v-else>
            <span>{{ value && value.name ? value.name : '' }}</span>
        </div>
    </div>

</template>

<script>
import BaseEntity from './base-ui/BaseEntity'

export default {
    name: 'ProductId',
    mixins:[BaseEntity],
    components:{
    },
    data: () => ({
        path: 'ProductIds',
    }),
    watch: {
        value(val){
            this.value = val;
            this.change();
        },
    },
    async created(){
        if (this.value && this.value.id !== undefined) {
            this.value = await this.repository.findById(this.value.id)
        }
    },
    methods: {
        pick(val){
            this.value = val;
            this.change();
        },
    }
}
</script>

