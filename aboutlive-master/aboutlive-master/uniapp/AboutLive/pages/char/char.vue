<template>
	<view>
		<view class="type">
			<lgd-tab :tabValue="tabValue" :tabIndex="tabIndex" @getIndex="tIndexClick" />
		</view>

		<view style="height: 200rpx;">

		</view>

		<view class="chat" v-if="tabIndex==0">

			<!-- 探索/匹配 -->

			<view style="text-align: center;margin-top: 120rpx;">
				<image src="../../static/love.png" mode="widthFix"></image>
			</view>

			<view style="text-align: center;margin-top: -150rpx;">
				<image src="../../static/information.png" mode="widthFix" style="width: 350rpx;"></image>
			</view>

			<view @click="match()">
				<view class="match">
					<view class="img1">
						<image src="../../static/index-png/Rocket.png" mode="widthFix"></image>
					</view>
					<view class="img">
						<image src="../../static/index-png/Planet.png" mode="widthFix"></image>
					</view>
					<view class="font">
						搜寻朋友 · 33616人在线
					</view>
					<view class="font1">
						探索最般配的人(剩余20次)
					</view>
				</view>
			</view>

		</view>

		<view class="chat" v-if="tabIndex==1">
			<view class="userList" v-for="(item,index) in userList" :key="index" @click="messageNav(index)">
				<view class="head">
					<image :src="item.headImage" mode="widthFix"></image>
				</view>
				<view style="width: 500rpx;">
					<view class="name">
						{{item.name}}
					</view>
					<view class="message">
						<text style="color: #04BE02;" v-if="onlineUserList.indexOf(item.userKey)!=-1">· 在线</text> [来自匹配]
					</view>
				</view>
				<view style="margin-top: 5rpx;">
					<view class="time">
						13:48
					</view>
					<view class="messageSum" style="margin-top: 25rpx;"
						v-if="noReadMessageNumber[index][1]!=null && noReadMessageNumber[index][1]!='0'">
						{{noReadMessageNumber[index][1]}}
					</view>
				</view>
				<view class="online" v-if="onlineUserList.indexOf(item.userKey)!=-1">

				</view>
			</view>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				tabValue: ['匹配', '聊天'],
				tabIndex: 0,
				messageUserIdList: [],
				userList: [],
				onlineUserList: [],
				noReadMessageNumber: [],
				matchIndex:0
			}
		},
		onPullDownRefresh() {
			var that = this;
			

			
			uni.request({
				data: {
					userKey: getApp().globalData.userKey
				},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Tool/match",
				success: function(res) {
			
					// console.log(res.data.data)
					for (var i = 0; i < res.data.data.length; i++) {
						// console.log(that.messageUserIdList.indexOf(res.data.data[i]))
						if (that.messageUserIdList.indexOf(res.data.data[i]) == -1) {
							that.messageUserIdList.push(res.data.data[i])
						}
						// console.log(res.data.data[i])
						// console.log(that.messageUserIdList)
					}
					uni.setStorage({
						key: "messageUserIdList",
						data: JSON.stringify(that.messageUserIdList)
					})
			
				}
			})
			
			for (var i = 0; i < that.messageUserIdList.length; i++) {
				that.noReadMessageNumber.push(["", "0"])
			}
			
			uni.request({
				data: {
					userKey: that.messageUserIdList
				},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/User/selectUserList",
				success: function(res) {
					if (res.data.code == 0) {
						that.userList = res.data.data
						// console.log(that.userList)
					} else {
						uni.showToast({
							title: '好像什么都没查到呢...',
							icon: 'none',
							duration: 2000
						});
					}
				}
			})
			
			uni.stopPullDownRefresh();
		},
		onLoad() {
			uni.showLoading({
				title: '加载中...'
			});
			var that = this;

			uni.getStorage({
				key: "messageUserIdList",
				success: function(res) {
					that.messageUserIdList = JSON.parse(res.data)
				}
			})

			uni.removeStorage({
				key: 'messageUserIdList',
				success() {
					// console.log('删除成功')
				}
			})

			uni.request({
				data: {
					userKey: getApp().globalData.userKey
				},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Tool/match",
				success: function(res) {

					// console.log(res.data.data)
					for (var i = 0; i < res.data.data.length; i++) {
						// console.log(that.messageUserIdList.indexOf(res.data.data[i]))
						if (that.messageUserIdList.indexOf(res.data.data[i]) == -1) {
							that.messageUserIdList.push(res.data.data[i])
						}
						// console.log(res.data.data[i])
						// console.log(that.messageUserIdList)
					}
					uni.setStorage({
						key: "messageUserIdList",
						data: JSON.stringify(that.messageUserIdList)
					})

				}
			})

			for (var i = 0; i < that.messageUserIdList.length; i++) {
				that.noReadMessageNumber.push(["", "0"])
			}
			// uni.removeStorage({
			// 	key: 'messageUserIdList',
			// 	success() {
			// 		// console.log('删除成功')
			// 	}
			// })
			// uni.setStorage({
			// 	key: "messageUserIdList",
			// 	data: JSON.stringify(['adw1df23dwa4d5d6'])
			// })
			// uni.getStorage({
			// 	key: "messageUserIdList",
			// 	success: function(res) {
			// 		that.messageUserIdList = JSON.parse(res.data)
			// 	}
			// })

			uni.request({
				data: {
					userKey: that.messageUserIdList
				},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/User/selectUserList",
				success: function(res) {
					if (res.data.code == 0) {
						that.userList = res.data.data
						// console.log(that.userList)
					} else {
						uni.showToast({
							title: '好像什么都没查到呢...',
							icon: 'none',
							duration: 2000
						});
					}
				}
			})


			uni.hideLoading();
		},

		onShow() {

			var that = this;

			uni.getStorage({
				key: "messageUserIdList",
				success: function(res) {
					that.messageUserIdList = JSON.parse(res.data)
				}
			})

			uni.removeStorage({
				key: 'messageUserIdList',
				success() {
					// console.log('删除成功')
				}
			})

			uni.request({
				data: {
					userKey: getApp().globalData.userKey
				},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Tool/match",
				success: function(res) {

					// console.log(res.data.data)
					for (var i = 0; i < res.data.data.length; i++) {
						// console.log(that.messageUserIdList.indexOf(res.data.data[i]))
						if (that.messageUserIdList.indexOf(res.data.data[i]) == -1) {
							that.messageUserIdList.push(res.data.data[i])
						}
						// console.log(res.data.data[i])
						// console.log(that.messageUserIdList)
					}
					uni.setStorage({
						key: "messageUserIdList",
						data: JSON.stringify(that.messageUserIdList)
					})


				}
			})

			// console.log(that.messageUserIdList.length)
			uni.request({
				data: {
					key: that.messageUserIdList
				},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Tool/online",
				success: function(res) {
					that.onlineUserList = res.data.data
					// console.log(that.onlineUserList)
					uni.request({
						data: {
							userKey: getApp().globalData.userKey,
							key: that.messageUserIdList
						},
						method: "GET",
						url: getApp().globalData.BaseUrl + "/Message/userListNoReadMessage",
						success: function(res) {
							that.noReadMessageNumber = res.data.data
							// console.log(res.data.data)
							// console.log(that.onlineUserList)
						}
					})
				}
			})



			this.timer = setInterval(() => {
				uni.request({
					data: {
						key: that.messageUserIdList,
						userKey: getApp().globalData.userKey
					},
					method: "GET",
					url: getApp().globalData.BaseUrl + "/Message/userListNoReadMessage",
					success: function(res) {
						that.noReadMessageNumber = res.data.data
						// console.log(that.onlineUserList)
					}
				})

				uni.request({
					data: {
						key: that.messageUserIdList
					},
					method: "GET",
					url: getApp().globalData.BaseUrl + "/Tool/online",
					success: function(res) {
						that.onlineUserList = res.data.data
						// console.log(that.onlineUserList)
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
		methods: {
			tIndexClick(rel) {
				
				this.tabIndex = rel
				// console.log(res)
			},
			messageNav(index) {
				var that = this;
				console.log(that.userList)
				uni.navigateTo({
					url: 'messageChar/messageChar?userKey=' + that.userList[index].userKey + "&name=" + that
						.userList[index].name
				})
			},
			match() {
				uni.showLoading({
					title: '正在匹配...'
				});
				var that = this;
				this.matchIndex = 0;
				uni.request({
					data: {
						userKey: getApp().globalData.userKey
					},
					method: "GET",
					url: getApp().globalData.BaseUrl + "/Tool/matching",
					success: function(res) {
						if (res.data.code != 1) {
							
							that.matching(res.data.code);
						}
						// console.log(that.onlineUserList)
					}
				})
			},

			matching(id) {
				if (id == 0) {
					this.timers = setInterval(() => {
						uni.request({
							data: {
								userKey: getApp().globalData.userKey
							},
							method: "GET",
							url: getApp().globalData.BaseUrl + "/Tool/matching",
							success: function(res) {
								if(res.data.code==0){
									uni.hideLoading();
									clearTimeout(this.timers);
									that.timers = null;
								}
							}
						})
						this.matchIndex=this.matchIndex+1;
						if(this.matchIndex>=15){
							clearTimeout(this.timers);
							this.timers = null;
						}
						// that.onlineUserList=[]
					}, 1000)
				} else {
					this.timers = setInterval(() => {
						uni.request({
							data: {
								userKey: getApp().globalData.userKey
							},
							method: "GET",
							url: getApp().globalData.BaseUrl + "/Tool/matchingBoolean",
							success: function(res) {
								if(res.data.code==0){
									uni.hideLoading();
									clearTimeout(this.timers);
									that.timers = null;
								}
								// console.log(that.onlineUserList)
							}
						})
						this.matchIndex=this.matchIndex+1;
						if(this.matchIndex>=15){
							clearTimeout(this.timers);
							this.timers = null;
						}
						// that.onlineUserList=[]
					}, 1000)
				}
			}
		}
	}
</script>

<style>
	page {
		font-weight: 26rpx;
		background-color: #f4f4f4;
	}

	.type {
		width: 100%;
		padding-top: 100rpx;
		margin: 0 auto;
		position: fixed;
		z-index: 2;
		top: 0;
		height: 100rpx;
		background-color: #f4f4f4;
	}

	.userList {
		display: flex;
		margin-top: 20rpx;
		padding: 20rpx;
	}

	.userList image {
		width: 100rpx;
		border-radius: 50%;
	}

	.userList .name {
		margin-left: 20rpx;
		font-size: 38rpx;
	}

	.userList .message {
		margin-left: 20rpx;
		margin-top: 10rpx;
		font-size: 28rpx;
		color: #949494;
	}

	.userList .online {
		background-color: #47ee7f;
		width: 30rpx;
		height: 30rpx;
		line-height: 30rpx;
		position: absolute;
		z-index: 1;
		border-radius: 50%;
		margin-left: 75rpx;
		margin-top: 72rpx;
	}

	.userList .time {
		font-size: 28rpx;
		color: #949494;
	}

	.userList .messageSum {
		background-color: #CD3333;
		color: #fff;
		border-radius: 50%;
		text-align: center;
		width: 40rpx;
		height: 40rpx;
		line-height: 40rpx;
		margin-top: 15rpx;
		font-size: 24rpx;
		margin-left: 30rpx;
	}

	.match {
		background-color: #212121;
		width: 80%;
		height: 20vw;
		left: 10%;
		text-align: center;
		border-radius: 3vw;
		position: fixed;
		bottom: 40vw;
		box-shadow: 2px 2px 25px rgba(0, 0, 0, 0.2);
	}

	.match .font {
		color: #fff;
		margin-top: 5vw;
		font-size: 3vw;
		font-weight: bold;
		margin-left: 8vw;
	}

	.match .font1 {
		color: #fff;
		margin-top: 1vw;
		font-size: 3vw;
		margin-left: 10vw;
	}

	.match .img image {
		width: 15vw;
		position: absolute;
		margin-left: -35vw;
		margin-top: 2vw;
	}

	.match .img1 image {
		width: 10vw;
		position: absolute;
		margin-left: -40vw;
		margin-top: -5vw;
		transform: rotate(45deg);
	}
</style>
