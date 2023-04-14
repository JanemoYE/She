<template>
	<view class="content">
		<view class="top flex">
			<view @click="onClickNavigator('./information/information')">
				<image src="../../static/User/offOn.png" mode="widthFix"></image>
			</view>
			<view @click="onClickNavigator('./setUp/setUp')">
				<image src="../../static/User/setUp.png" mode="widthFix"></image>
			</view>
		</view>
		<view class="userHead1" :style="'background: url('+userArrays[2]+');'">

		</view>
		<view class="userHead">
			<view class="">
				<image :src="userArrays[2]" mode="widthFix"></image>
			</view>
			<br>
		</view>

		<view class="collections">
			<view class="release" @click="onClickNavigator('../myRelease/myRelease')">
				<view class="flex" style="justify-content: space-around">
					<view
						style="background-color: #FFFFFF;border-radius: 20rpx;margin-left:-30rpx;width: 90rpx; height: 90rpx;text-align: center;">
						<image src="../../static/User/release.png" mode="widthFix" style="width: 80rpx;"></image>
					</view>
					<view style="width: 70rpx;">

					</view>
				</view>

				<view class="text">
					<view style="color: #fff;">
						Release 发布
					</view>
					<view style="color: gray;font-size: 25rpx;">
						All your releases
					</view>
				</view>

			</view>

			<view class="release" @click="onClickNavigator('../collection/collection')">
				<view class="flex" style="justify-content: space-around">
					<view
						style="background-color: #FFFFFF;border-radius: 20rpx;margin-left:-30rpx;width: 90rpx; height: 90rpx;text-align: center;">
						<image src="../../static/User/collections.png" mode="widthFix" style="width: 80rpx;"></image>
					</view>
					<view style="width: 70rpx;">

					</view>
				</view>
				<view class="text">
					<view style="color: #fff;">
						Collection 收藏
					</view>
					<view style="color: gray;font-size: 25rpx;">
						All yours-collections
					</view>
				</view>
			</view>
		</view>

		<view class="information flex" @click="allInformation()">
			<view class="text">
				All information
			</view>
			<view class="div flex">
				<view
					style="background-color: #fff;border-radius: 20rpx;padding: 0 15rpx;font-size: 25rpx;font-weight: bold;line-height: 40rpx;">
					{{read}}
				</view>
			</view>
		</view>

		<view class="hr">

		</view>
		<view style="display: flex;margin: 20rpx;">
			<view style="font-size: 28rpx;line-height: 60rpx;">我的热度</view>
			<view class="fire">
				<image src="../../static/User/fire.png" mode="widthFix" style="width: 50rpx;"></image>
			</view>
		</view>

		<view class="myFiery">
			<view class="images">
				<image :src="userArrays[2]" mode="widthFix"></image>
			</view>
			<view class="name">
				{{userArrays[1]}}
			</view>
		</view>

		<view class="myFieryList">

			<view class="type" @click="onClickNavigator('foucs/foucs')">
				<view style="font-weight: bold;text-align: center;">
					{{fousc}}
				</view>
				<view style="font-size: 26rpx;text-align: center;color: gray;">
					关注
				</view>
			</view>

			<view class="type" @click="onClickNavigator('fan/fan')"
				style="border-right: rgba(0,0,0,0.15) solid 1px;width: 250rpx;border-left: rgba(0,0,0,0.15) solid 1px;">
				<view style="font-weight: bold;text-align: center;">
					{{fan}}
				</view>
				<view style="font-size: 26rpx;text-align: center;color: gray;">
					粉丝
				</view>
			</view>

			<view class="type">
				<view style="font-weight: bold;text-align: center;">
					<view class="lv" :class="'Exe'+Exe">
						<image src="../../static/lv.png" mode="widthFix"></image>
						<view>
							Lv-{{Exe}}
						</view>
					</view>
				</view>
				<view style="font-size: 26rpx;text-align: center;color: gray;" @click="prestigeNav()">
					声望
				</view>
			</view>
		</view>
		
		<view style="height: 250rpx;"></view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userArrays: {},
				fousc: 0,
				fan: 0,
				Exe: 1,
				read:0
			}
		},
		onPullDownRefresh() {
			
			if (getApp().globalData.userKey == "") {
				uni.reLaunch({
					url: "../login/login"
				})
			} else {
				var that = this;
				this.userArrays = getApp().globalData.userArrays
				uni.request({
					data: {
						userKey: getApp().globalData.userKey
					},
					method: "GET",
					url: getApp().globalData.BaseUrl+"/Article/getInformationCount",
					success: function(res) {
						
						if(res.data.data>99){
							that.read=99
						}else{
							that.read=res.data.data
						}
						// console.log(res.data.data)
					}
				})
				uni.request({
					data: {
						userKey: getApp().globalData.userKey
					},
					method: "GET",
					url: getApp().globalData.BaseUrl+"/User/myFiery",
					success: function(res) {
						that.fousc = res.data.data[0];
						that.fan = res.data.data[1];
						that.Exe = res.data.data[2];
						uni.stopPullDownRefresh();
					}
				})
				
			}
		},
		onLoad() {
			uni.getStorage({
			    key:"userKey",
			    success: function(res){
			        getApp().globalData.userKey=JSON.parse(res.data)
			    }
			})
			uni.getStorage({
			    key:"userArrays",
			    success: function(res){
			        getApp().globalData.userArrays=JSON.parse(res.data)
			    }
			})
			if (getApp().globalData.userKey == "") {
				uni.reLaunch({
					url: "../login/login"
				})
			} else {
				uni.showLoading({
					title: '加载中...'
				});
				var that = this;
				this.userArrays = getApp().globalData.userArrays
				uni.request({
					data: {
						userKey: getApp().globalData.userKey
					},
					method: "GET",
					url: getApp().globalData.BaseUrl+"/Article/getInformationCount",
					success: function(res) {
						
						if(res.data.data>99){
							that.read=99
						}else{
							that.read=res.data.data
						}
						// console.log(res.data.data)
					}
				})
				uni.request({
					data: {
						userKey: getApp().globalData.userKey
					},
					method: "GET",
					url: getApp().globalData.BaseUrl+"/User/myFiery",
					success: function(res) {
						that.fousc = res.data.data[0];
						that.fan = res.data.data[1];
						that.Exe = res.data.data[2];
					}
				})
				uni.hideLoading();
			}
		},
		methods: {
			onClickNavigator(code) {
				uni.navigateTo({
					url: code
				})
			},
			allInformation(){
				uni.navigateTo({
					url:'./allInformation/allInformation'
				})
			},
			prestigeNav(){
				uni.navigateTo({
					url:'./prestige/prestige'
				})
			}
		}
	}
</script>

<style>
	page{
		font-size: 26rpx;
	}

	.top {
		padding: 30rpx;
		padding-top: 60rpx;
		justify-content: space-between;
	}

	.top image {
		width: 50rpx;
	}

	.userHead1 {
		height: 400rpx;
		width: 95%;
		margin: 0 auto;
		border-radius: 50rpx;
		filter: blur(10px);
	}

	.userHead {
		margin-top: -300rpx;
		text-align: center;
	}

	.userHead image {
		width: 200rpx;
		border-radius: 50%;
	}

	.collections {
		margin-top: 100rpx;
		display: flex;
		justify-content: space-around;
	}

	.flex {
		display: flex;
	}

	.collections .release {
		background-color: #2c2c2c;
		width: 320rpx;
		padding: 20rpx;
		border-radius: 30rpx;
	}

	.collections .release .text {
		margin-top: 20rpx;
		margin-left: 20rpx;
	}

	.information {
		background-color: #2C2C2C;
		width: 90%;
		margin: 0 auto;
		margin-top: 10rpx;
		border-radius: 20rpx;
		padding: 20rpx 30rpx;
		justify-content: space-between;
	}

	.information .text {
		color: #FFFFFF;
		font-size: 28rpx;
	}

	.myFiery {
		display: flex;
	}

	.myFiery .images image {
		width: 80rpx;
		border-radius: 50%;
		margin-left: 20rpx;
	}

	.myFiery .name {
		margin-top: 15rpx;
		margin-left: 15rpx;
	}

	.hr {
		width: 95%;
		background-color: rgba(0, 0, 0, 0.1);
		height: 1px;
		margin: 0 auto;
		margin-top: 30rpx;
	}

	.myFieryList {
		display: flex;
		width: 80%;
		margin: 0 auto;
		margin-top: 50rpx;
		justify-content: space-between;
	}
</style>
