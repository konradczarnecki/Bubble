
const exchange = Vue.component('exchange', {

    template : `
        <div class="top">
        <div class="exchange">
            <div class="item" v-for="(item, index) in items" :key="item.id" @mouseover="hover(index)"
                 @mouseleave="leave(index)" :style="{'backgroundImage' : 'url(' + item.image + ')'}">
                <div v-if="item.showOverlay" class="overlay" @click="buy(item.id, index)">{{item.description}}</div>
                <p v-if="!item.showOverlay" class="name">{{item.name}}</p>
                <p v-if="!item.showOverlay" class="price">{{item.price}}</p>
            </div>
        </div></div>`,

     data : function() {
        return {
            items : []
        }
     },

     mounted : function () {

        axios.request({
            url: '/items',
            method : 'get',
            headers : {
                token : sessionStorage.getItem('token')
            }
        }).then(response => {

            this.items = response.data;
            console.log(response.data);
        })
     },

     methods : {

         hover : function (index) {

            let arr = this.items.slice();
            arr[index].showOverlay = true;
            this.items = arr;
         },

         leave : function (index) {

            let arr = this.items.slice();
            arr[index].showOverlay = false;
            this.items = arr;
         },

         buy : function (itemId, index) {

             axios.request({
                 url : '/buy',
                 method : 'post',
                 headers : {
                     token : sessionStorage.getItem('token')
                 },
                 params : {
                     id : itemId
                 }
             }).then(response => {

                 eventBus.$emit('updateBalance', newVal);

             })
         }
     }

 });
