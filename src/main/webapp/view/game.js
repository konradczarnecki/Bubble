const eventBus = new Vue({});

const game = Vue.component('game', {

    template : `
            <div class="game">
                <div class="outline" @click.left="bet" @contextmenu="redeem($event)"></div>
                <main-bubble :id="bubble.id" :key="bubble.id" :size="bubble.progress" :max="bubble.max"
                              @update="update" :expires="bubble.expires"></main-bubble>
            </div>`,

    data : function () {
        return {
            bubble : {},
            balance : Number(sessionStorage.getItem('balance')),
            prize : 0
        }
    },

    mounted : function () {
        this.update();
    },
    
    watch : {
      balance : function (newValue, oldValue) {
          eventBus.$emit('updateBalance', newValue);
      }  
    },

    methods : {

        update : function () {
            axios.request({
                url : '/gamestate',
                method : 'get',
                headers : {
                    token : sessionStorage.getItem('token')
                }
            }).then(response => {

                console.log(response.data);

                this.balance = response.data.balance.value;
                let newBubble = response.data.bubble;

                if(newBubble.id != this.bubble.id) this.bubble = newBubble;
                else setTimeout(this.update, 600);
            })
        },

        bet : function () {

            let wager = 100;

            axios.request({
                url : '/bet',
                method : 'post',
                params : {
                    amount : wager
                }
            }).then(response => {
                console.log(response);

                if(response.data.status == 'success'){
                    this.balance -= wager;
                }
            })
        },

        redeem : function (event) {

            event.preventDefault();

            axios.request({
                url : '/redeem',
                method : 'post',
                headers : {
                    token : sessionStorage.getItem('token')
                },
                params : {
                    stamp : Date.now(),
                    tkn : 'abc',
                    balance : this.balance,
                    bubble : this.bubble.id
                }
            }).then(response => {

                console.log(response);

                if(response.data.status == 'success'){
                    let prize = this.balance - response.data.item;
                    this.balance = response.data.item;
                }

            }).catch(error => {
                console.log(error);
            })
        }
    }
});

Vue.component('main-bubble', {

    template : `<div v-show="visible" id="mainBubble"></div>`,

    props : ['id', 'size', 'max', 'expires'],

    data : function () {
        return {
            visible : true
        }},

    mounted : function () {

        let start = this.size / 100;
        let end = this.max / 100;
        let duration = this.expires - Date.now();
        this.visible = true;

        anime({
            targets : '#mainBubble',
            scale : [start, end],
            duration : duration,
            easing : 'linear',
            complete: () => {
                this.visible = false;
                this.$emit('update');
            }
        })
    }
});
