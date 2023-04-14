<template>
	<view>
		<view class="title">
			<view class="back" @click="back()">
				<image src="../../../static/back.png" mode="widthFix"></image>
			</view>
			<view class="type">
				<view class="noneTypeFont">
					新话题
				</view>
				<view class="blockTypeFont">
					加入的话题
				</view>
			</view>
		</view>
		
		<view class="allTopic">
			<view class="topicImage">
				<image src="https://tse4-mm.cn.bing.net/th/id/OIP-C.DNu1nD6pmEK6IU8d-kTS5AHaHa?pid=ImgDet&rs=1" mode="widthFix"></image>
			</view>
			<view>
				<view class="titleTopic">
					"来看看谁是话题终结者"
				</view>
				<view style="margin-left: 25rpx;color: gray;font-size: 22rpx;margin-top: 5rpx;">
					由西楚' 建立
				</view>
				<view style="display: flex;">
					<view class="user">
						<view class="image">
							<image src="https://q1.qlogo.cn/g?b=qq&nk=861019280&s=640" mode="widthFix"></image>
						</view>
						<view class="gd">
							...
						</view>
						<view style="color: gray;margin-top: 20rpx;font-size: 22rpx;margin-left: 10rpx;">
							共69人
						</view>
					</view>
					<view class="add">
						+ 加入
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
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
		background-color: #f7f7f7;
		font-size: 26rpx;
	}
	.title{
		background-color: #FFFFFF;
		display: flex;
		padding: 40rpx;
		padding-top: 80rpx;
	}
	.title .back image{
		width: 40rpx;
	}
	.title .back{
		width: 180rpx;
	}
	.title .type{
		display: flex;
	}
	.noneTypeFont{
		color: #bdbdbd;
		/* font-weight: bold; */
		font-size: 28rpx;
		margin-top: 3rpx;
		margin-left: 20rpx;
		margin-right: 20rpx;
	}
	.blockTypeFont{
		color: #000;
		font-weight: bold;
		/* font-weight: bold; */
		font-size: 34rpx;
		margin-left: 20rpx;
		margin-right: 20rpx;
		border-bottom: #212121 3px solid;
	}
	
	.allTopic{
		display: flex;
		width: 85%;
		margin: 0 auto;
		border-radius: 15rpx;
		margin-top: 20rpx;
		background-color: #fff;
		padding: 30rpx;
	}
	.allTopic .topicImage{
		width: 150rpx;
		height: 150rpx;
		overflow: hidden;
		border-radius: 15rpx;
	}
	.allTopic .topicImage image{
		width: 150rpx;
	}
	.allTopic .titleTopic{
		/* font-weight: bold; */
		font-size: 30rpx;
		margin-left: 20rpx;
	}
	.gd{
		background-color: #e6efff;
		color: #007AFF;
		width: 20rpx;
		height: 20rpx;
		padding: 20rpx;
		line-height: 8rpx;
		border-radius: 50%;
		border: #fff 4rpx solid;
		margin-left: -15rpx;
		z-index: 2;
	}
	.allTopic .user{
		display: flex;
		margin-left: 20rpx;
		margin-top: 10rpx;
		width: 350rpx;
	}
	.allTopic .user .image image{
		width: 55rpx;
		border: #fff 4rpx solid;
		border-radius: 50%;
	}
	.add{
		background-color: #212121;
		color: #fff;
		width: 120rpx;
		height: 50rpx;
		line-height: 50rpx;
		text-align: center;
		border-radius: 50rpx;
		margin-top: 20rpx;
	}
	.noAdd{
		background-color: #efefef;
		color: gray;
		width: 120rpx;
		height: 50rpx;
		line-height: 50rpx;
		text-align: center;
		border-radius: 50rpx;
		margin-top: 20rpx;
	}
</style>
