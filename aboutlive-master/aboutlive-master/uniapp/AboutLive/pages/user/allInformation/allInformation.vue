<template>
	<view>
		<view class="titleTop">
			<view class="back" @click="back()">
				<image src="../../../static/back.png" mode="widthFix"></image>
			</view>
			<view class="title">
				所有消息
			</view>
			<view style="width: 50rpx;"></view>
		</view>

		<view class="list">
			<view @click="commentNav()">
				<view class="likeNumber" v-if="commentNumber!=0">
					{{commentNumber}}
				</view>
				<image src="../../../static/allInfor/comment.png" mode="widthFix"></image>
				<view style="color: #FFFFFF;text-align: center;margin-top: 10rpx;">评论</view>
			</view>
			<view @click="likeNav()">
				<view class="likeNumber" v-if="likeNumber!=0">
					{{likeNumber}}
				</view>
				<image src="../../../static/allInfor/good.png" mode="widthFix"></image>
				<view style="color: #FFFFFF;text-align: center;margin-top: 10rpx;">
					点赞
				</view>
			</view>
		</view>
		
		<view class="hint">
			系统消息
		</view>
		
		<view v-for="(item,index) in sysList" :key="index">
			<view class="systemInfor">
				<view class="read" v-if="item.read==1">NEW</view>
				<view class="title">
					<view style="background-color: #F0AD4E;border-radius: 50%;width: 80rpx;height: 80rpx;text-align: center;">
						<image src="../../../static/add/peopleIcon.png" mode="widthFix"></image>
					</view>
					<view style="margin-top: 16rpx;margin-left: 20rpx;font-size: 30rpx;color: #fff;">
						消息提醒
					</view>
				</view>
				<view class="content">
					{{item.content}}
				</view>
				<view class="get">
					查看详细
				</view>
			</view>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				commentNumber:0,
				likeNumber:0,
				sysList:{}
			}
		},
		onLoad() {
			uni.showLoading({
				title: '加载中...'
			});
			var that = this;
			uni.request({
				data: {
					userKey: getApp().globalData.userKey,
					type:"点赞"
				},
				method: "GET",
				url: getApp().globalData.BaseUrl+"/Article/readCount",
				success: function(res) {
					
					that.likeNumber=res.data.data
					// console.log(res.data.data)
					uni.request({
						data: {
							userKey: getApp().globalData.userKey,
							type:"评论"
						},
						method: "GET",
						url: getApp().globalData.BaseUrl+"/Article/readCount",
						success: function(res) {
							
							that.commentNumber=res.data.data
							// console.log(res.data.data)
							uni.request({
								data: {
									type:"系统"
								},
								method: "GET",
								url: getApp().globalData.BaseUrl+"/Article/getSystemInformation",
								success: function(res) {
									
									that.sysList=res.data.data
									// console.log(res.data.data)
									uni.hideLoading();
								}
							})
						}
					})
				}
			})
			
			
			
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			likeNav(){
				uni.navigateTo({
					url:'./likeInformation/likeInformation'
				})
			},
			commentNav(){
				uni.navigateTo({
					url:'./commentInformation/commentInformation'
				})
			}
		}
	}
</script>

<style>
	page {
		font-size: 26rpx;
		background-color: #181818;
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
		z-index: 2;
		background-color: #181818;
		color: #fff;
		padding-top: 60rpx;
		width: 90%;
		margin-top: 20rpx;
		display: flex;
		justify-content: space-between;
		position: fixed;
		top: -20rpx;
		left: 40rpx;
		padding-bottom: 20rpx;
	}
	.titleTop .back{
		background-color: #fff;
		width: 80rpx;
		height: 80rpx;
		text-align: center;
		border-radius: 50%;
	}
	.titleTop .back image {
		margin-top: 20rpx;
		width: 40rpx;
	}

	.titleTop .title {
		margin-top: 20rpx;
		font-weight: bold;
	}


	.list {
		display: flex;
		width: 80%;
		margin: 0 auto;
		margin-top: 200rpx;
		justify-content: space-around;
		background-color: #212121;
		padding: 30rpx;
		border-radius: 50rpx;
		box-shadow: 2px 5px 8px rgba(0,0,0,0.3);
		border: gray solid 1rpx;
	}

	.list image {
		width: 120rpx;
	}
	.hint{
		color: gray;
		margin-top: 20rpx;
		margin-left: 50rpx;
	}
	.systemInfor{
		box-shadow: 2px 5px 8px rgba(0,0,0,0.3);
		background-color: #212121;
		border-radius: 20rpx;
		padding: 30rpx;
		width: 80%;
		border: gray solid 1rpx;
		margin: 0 auto;
		margin-top: 50rpx;
	}
	.systemInfor .title{
		display: flex;
	}
	.systemInfor .title image{
		width: 50rpx;
		margin-top: 12rpx;
	}
	.systemInfor .content{
		color: #fff;
		margin-top: 30rpx;
		margin-left: 10rpx;
	}
	.systemInfor .get{
		margin-top: 40rpx;
		width: 120rpx;
		padding: 10rpx;
		text-align: center;
		border-radius: 35rpx;
		background-color: #fff;
		font-size: 24rpx;
	}
	.systemInfor .read{
		background-color: red;
		width: 120rpx;
		height: 40rpx;
		border-radius: 20rpx;
		color: #fff;
		text-align: center;
		line-height: 40rpx;
		font-size: 23rpx;
		position: absolute;
		z-index: 1;
		margin-left: 480rpx;
		margin-top: 20rpx;
	}
	.likeNumber{
		z-index: 1;
		background-color: #CD3333;
		position: absolute;
		margin-left: 80rpx;
		color: #fff;
		width: 35rpx;
		height: 35rpx;
		border-radius: 50%;
		text-align: center;
		line-height: 30rpx;
		padding: 3rpx;
	}
</style>
