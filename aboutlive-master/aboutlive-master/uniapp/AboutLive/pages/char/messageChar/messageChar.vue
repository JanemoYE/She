<template>
	<view>
		<view class="titleTop">
			<view class="back" @click="back()">
				<image src="../../../static/back.png" mode="widthFix"></image>
			</view>
			<view class="img" style="margin-top: -30rpx;">
				<image :src="user.headImage" mode="widthFix"></image>
			</view>
			<view style="width: 200rpx;">
				<view class="title">
					{{typeString}}
				</view>
				<view class="lv" :class="'Exe'+6" style="margin-top: 10rpx;">
					<image src="../../../../static/lv.png" mode="widthFix"
						style="width: 30rpx;margin-left: 5rpx;margin-top: 5rpx;"></image>
					<view>
						Lv-{{6}}
					</view>
				</view>
			</view>
			<view class="" style="margin-left: 50rpx;">
				<image src="../../../static/call.png" mode="widthFix" style="width: 60rpx;"></image>
			</view>
			<view class="" style="margin-left: 50rpx;">
				<image src="../../../static/video.png" mode="widthFix" style="width: 60rpx;"></image>
			</view>
			<view class="" style="margin-left: 50rpx;">
				<image src="../../../static/gd.png" mode="widthFix" style="width: 60rpx;"></image>
			</view>
			<view class="save"></view>
		</view>

		<view style="height: 200rpx;">

		</view>

		<view class="messageContent">
			<view v-for="(item,index) in historyMessage" :key="index">
				<view class="leftUser" v-if="item[0]!=userKey">
					<text class="message">
						{{item[1]}}
					</text>
				</view>
				<view class="rightUser" v-else>
					<text class="message">
						{{item[1]}}
					</text>
				</view>
			</view>
		</view>

		<view class="inputMessage">
			<view>
				<input type="text" :value="content" placeholder="Type a message" @input="contentInput($event)" />
			</view>
			<view class="btn" @click="contentBtn()">
				发送
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				typeString: "",
				user: {},
				userKey: "",
				userKey1: "",
				historyMessage: [],
				content: "",
				storageName: ""
			}
		},
		onShow() {
			var that=this;
			this.timer = setInterval(() => {
				uni.request({
					data: {
						userKey: that.userKey,
						userKey1: that.userKey1
					},
					method: "GET",
					url: getApp().globalData.BaseUrl + "/Message/read",
					success: function(res) {
						if (res.data.code == 0) {
							// console.log(res.data)
							for (var i = 0; i < res.data.data.length; i++) {
								that.historyMessage.push(res.data.data[i])
							}
							// console.log(res.data.data)
							// console.log(res.data.data)
						} else if (res.data.code == 1) {
				
						}
					}
				})
				// that.onlineUserList=[]
			}, 10000)
		},
		onHide() {
			// 移除监听事件    
			if (this.timer) {
				clearTimeout(this.timer);
				this.timer = null;
			}
		
		},
		onLoad(str) {
			uni.showLoading({
				title: '加载中...'
			});
			var that = this;
			// uni.setStorage({
			// 	key: "11historyMessage",
			// 	data: JSON.stringify()
			// })
			this.userKey=getApp().globalData.userKey
			// console.log(str.userKey)
			this.typeString = str.name
			this.userKey1 = str.userKey
			
			this.storageName = this.userKey1 + "historyMessage"
			console.log("storage:"+this.storageName)
			
			uni.getStorage({
				key: that.storageName,
				success: function(res) {
					that.historyMessage = JSON.parse(res.data)
					// console.log("test:" + res.data)
				}
			})
			// this.userKey=getApp().globalData.userKey
			// console.log(this.historyMessage)
			uni.request({
				data: {
					userKey: that.userKey1
				},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/User/selectUser",
				success: function(res) {
					if (res.data.code == 0) {
						that.user = res.data.data

					} else {
						uni.showToast({
							title: '连接失败！',
							icon: 'none',
							duration: 2000
						});
					}

				}
			})
			uni.request({
				data: {
					userKey: that.userKey,
					userKey1: that.userKey1
				},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Message/read",
				success: function(res) {
					if (res.data.code == 0) {
						// console.log(res.data)
						for (var i = 0; i < res.data.data.length; i++) {
							that.historyMessage.push(res.data.data[i])
						}
						// console.log(res.data.data)
						// console.log(res.data.data)
					} else if (res.data.code == 1) {

					} else {
						uni.showToast({
							title: '获取失败！',
							icon: 'none',
							duration: 2000
						});
					}
					uni.hideLoading();
				}
			})
		},
		methods: {
			back() {
				// 移除监听事件
				if (this.timer) {
					clearTimeout(this.timer);
					this.timer = null;
				}
				uni.navigateBack({
					delta: 1
				})
			},
			comment(id) {
				uni.navigateTo({
					url: "../../comment/comment?id=" + id
				});
			},
			contentInput(value) {
				this.content = value.detail.value
			},
			contentBtn() {
				var that = this;
				that.historyMessage.push([that.userKey, that.content])

				uni.setStorage({
					key: that.storageName,
					data: JSON.stringify(that.historyMessage)
				})

				uni.request({
					data: {
						userKey: that.userKey,
						userKey1: that.userKey1,
						content: that.content
					},
					method: "POST",
					header: {
						'Content-Type': 'application/x-www-form-urlencoded'
					},
					url: getApp().globalData.BaseUrl + "/Message/message",
					success: function(res) {
						if (res.data.code == 0) {
							that.content = ""
						} else if(res.data.code==3){

							uni.showToast({
								title: '有消息未接收！',
								icon: 'none',
								duration: 2000
							});
							
						}else {
							uni.showToast({
								title: '发送失败！',
								icon: 'none',
								duration: 2000
							});
							that.historyMessage.pop();
						}

					}
				})
			}
		}
	}
</script>

<style>
	page {
		background-color: #F4F4F4;
	}

	.titleTop {
		z-index: 1;
		padding-top: 100rpx;
		width: 100%;
		display: flex;
		/* justify-content: space-between; */
		padding-left: 20rpx;
		position: fixed;
		top: 0;
		background-color: #fff;
		border-bottom: #f1f1f1 solid 1px;
		padding-bottom: 20rpx;
	}

	.titleTop .back image {
		width: 40rpx;
		margin-left: 20rpx;
	}

	.titleTop .title {
		/* font-weight: bold; */
		font-size: 34rpx;
		margin-top: -30rpx;
		margin-left: 20rpx;
		display: block;
		/*内联对象需加*/
		/* width:31em;/*指定宽度*/
		word-break: keep-all;
		/* 不换行 */
		white-space: nowrap;
		/* 强制在同一行内显示所有文本，直到文本结束或者遭遇 br 对象。不换行 */
		overflow: hidden;
		/* 内容超出宽度时隐藏超出部分的内容 */
		text-overflow: ellipsis;
		/* IE 专有属性，当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。*/
	}

	.titleTop .save {
		width: 70rpx;
		margin-top: 6rpx;
	}

	.titleTop .img image {
		width: 100rpx;
		border-radius: 40rpx;
		margin-left: 50rpx;
	}

	.inputMessage {
		display: flex;
		padding: 30rpx 0;
		position: fixed;
		bottom: 0;
		width: 100%;
		z-index: 1;
		background-color: #fff;
	}

	.inputMessage input {
		width: 450rpx;
		margin-left: 50rpx;
		border-radius: 40rpx;
		padding: 20rpx;
		/* height: 60rpx; */
		background-color: #F1F1F1;
		color: #000;
	}

	.inputMessage .btn {
		width: 100rpx;
		background-color: #212121;
		color: #fff;
		border-radius: 20rpx;
		text-align: center;
		height: 60rpx;
		line-height: 55rpx;
		margin-left: 50rpx;
		padding: 10rpx;
	}

	.messageContent {
		overflow: auto;
		/* display: -webkit-box; */
		/* -webkit-overflow-scrolling: touch; */
		height: 1280rpx;
	}

	.leftUser {
		margin-top: 50rpx;
		width: 65%;
	}

	.rightUser {
		margin-top: 50rpx;
		margin-left: 260rpx;
		width: 65%;
		text-align: right;
	}

	.leftUser .message {
		background-color: #fff;
		padding: 20rpx;
		margin-top: 20rpx;
		border-top-right-radius: 20rpx;
		border-bottom-left-radius: 20rpx;
		border-bottom-right-radius: 30rpx;
		padding-left: 30rpx;
		padding-right: 30rpx;
		margin-left: 10rpx;
	}

	.rightUser .message {
		background-color: #66ff25;
		padding: 20rpx;
		margin-top: 20rpx;
		border-top-left-radius: 20rpx;
		border-bottom-left-radius: 30rpx;
		border-bottom-right-radius: 20rpx;
		padding-right: 30rpx;
		padding-left: 30rpx;
		margin-right: 10rpx;
	}
</style>
