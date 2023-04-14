<template>
	<view>
		<view class="titleTop">
			<view class="back" @click="back()">
				<image src="../../../static/back.png" mode="widthFix"></image>
			</view>
			<view class="title">
				声望
			</view>
			<view class="save"></view>
		</view>
		
		<view class="user">
			<view class="image">
				<image :src="userArrays[2]" mode="widthFix"></image>
			</view>
			<view class="type">
				<view style="font-weight: bold;text-align: center;">
					<view class="lv" :class="'Exe'+Exe">
						<image src="../../../static/lv.png" mode="widthFix"></image>
						<view>
							Lv-{{Exe}}
						</view>
					</view>
				</view>
				<view style="font-size: 26rpx;text-align: center;color: gray;margin-top: 30rpx;">
					声望等级
				</view>
			</view>
		</view>
		<!-- 百分比进度条 -->
		<view class="bf">
			<view class="nowBf" :style="'width:'+bfb+'%'">
				<view style="margin-left: 10rpx;">
					{{bfb}}%
				</view>
			</view>
		</view>
		
		<view class="font">
			距离升级还差 {{delExp}} 经验
		</view>
		
<!-- 		<view class="infor">
			<view class="like">
				<view class="font" style="text-align: center;margin: 0;color: #fff;margin-bottom: 20rpx;">
					获赞
				</view>
				<view class="image">
					<image src="../../../static/likes.png" mode="widthFix"></image>
				</view>
				<view class="number">
					18
				</view>
			</view>
		</view> -->
		
<!-- 		<view class="notice">
			
		</view> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userArrays:{},
				Exe:1,
				delExp:0, //差多少经验
				bfb:0 //百分比
			}
		},
		onPullDownRefresh() {
			
			if (getApp().globalData.userKey == "") {
				uni.reLaunch({
					url: "../../login/login"
				})
			} else {
				var that = this;
				this.userArrays = getApp().globalData.userArrays
				console.log(this.userArrays)
				uni.request({
					data: {
						userKey: getApp().globalData.userKey
					},
					method: "GET",
					url: getApp().globalData.BaseUrl+"/User/selectUserExperience",
					success: function(res) {
						// console.log(res.data.data)
						that.delExp=res.data.data[1]
						that.bfb=res.data.data[0]
					}
				})
				uni.request({
					data: {
						userKey: getApp().globalData.userKey
					},
					method: "GET",
					url: getApp().globalData.BaseUrl+"/User/myFiery",
					success: function(res) {
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
					url: "../../login/login"
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
					url: getApp().globalData.BaseUrl+"/User/selectUserExperience",
					success: function(res) {
						// console.log(res.data.data)
						that.delExp=res.data.data[1]
						that.bfb=res.data.data[0]
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
						uni.hideLoading();
					}
				})
				
			}
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
		}
	}
</script>

<style>
	page{
		font-size: 26rpx;
	}
	.bf{
		background-color: rgba(0,0,0,0.15);
		height: 30rpx;
		width: 80%;
		border-radius: 50rpx;
		margin: 0 auto;
		margin-top: 150rpx;
	}
	.nowBf{
		background-color: #f9af5a;
		height: 30rpx;
		border-radius: 50rpx;
		color: #fff;
		font-size: 22rpx;
	}
	.hr {
		width: 100%;
		background-color: rgba(0, 0, 0, 0.1);
		height: 1px;
		margin: 0 auto;
		margin-top: 30rpx;
		margin-bottom: 30rpx;
	}
	
	.titleTop {
		background-color: #fff;
		z-index: 2;
		padding: 40rpx;
		padding-top: 80rpx;
		width: 100%;
		display: flex;
		justify-content: space-between;
		position: fixed;
		top: 0;
		left: 0rpx;
	}
	
	.titleTop .back image {
		width: 40rpx;
	}
	
	.titleTop .title {
		font-weight: bold;
	}
	
	.titleTop .save {
		font-size: 24rpx;
		margin-top: 6rpx;
		width: 100rpx;
	}
	.user{
		margin-top: 200rpx;
		text-align: center;
	}
	.user .image image{
		width: 120rpx;
		border-radius: 50%;
	}
	.user .type{
		position: absolute;
		width: 150rpx;
		margin: 0 auto;
		margin-left: 300rpx;
		margin-top: -30rpx;
	}
	.font{
		color: gray;
		margin-left: 70rpx;
		margin-top: 20rpx;
		font-size: 22rpx;
	}
	.infor{
		background-color: #212121;
		width: 90%;
		border-radius: 30rpx;
		margin: 0 auto;
		margin-top: 20rpx;
		padding: 20rpx;
	}
	.infor .like image{
		width: 60rpx;
	}
	.like{
		text-align: center;
		margin-top: 20rpx;
	}
	.number{
		color: #fff;
		font-weight: bold;
		font-size: 36rpx;
		margin: 20rpx;
	}
/* 	.notice{
		background-color: #212121;
		width: 90%;
		padding: 20rpx;
		height: 500rpx;
		margin: 0 auto;
		margin-top: 20rpx;
		border-radius: 50rpx;
	} */
</style>
