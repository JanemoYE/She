<template>
	<view>
		<view class="titleTop">
			<view class="back" @click="back()">
				<image src="../../../../static/back.png" mode="widthFix"></image>
			</view>
			<view class="title">
				收到的赞
			</view>
			<view style="width: 50rpx;"></view>
		</view>

		<view class="hint">
			最新
		</view>
		
		<view v-if="newLikeList==''" style="color: gray;font-size: 24rpx;width: 100%;text-align: center;">
			暂无最新的点赞信息
		</view>
		
		<view v-for="(item,index) in newLikeList" :key="index">
			<view class="content">
				<view class="image">
					<image :src="item[2]" mode="widthFix"></image>
				</view>
				<view class="title">
					<text style="color: #fff;">{{item[3]}}</text>
					<text style="color: gray;margin-left: 20rpx;">{{item[0]}}</text>
				</view>
				<view class="articleImage" @click="articleNav(item[1])">
					<image
						:src="item[5]"
						mode="widthFix"></image>
				</view>
			</view>
		</view>
		
		<view class="hint" style="margin-top: 35rpx;">
			近二十条
		</view>
		
		<view v-if="LikeList==''" style="color: gray;font-size: 24rpx;width: 100%;text-align: center;">
			暂无点赞信息
		</view>
		
		<view v-for="(item,index) in LikeList" :key="index">
			<view class="content">
				<view class="image">
					<image :src="item[2]" mode="widthFix"></image>
				</view>
				<view class="title">
					<text style="color: #fff;">{{item[3]}}</text>
					<text style="color: gray;margin-left: 20rpx;">{{item[0]}}</text>
				</view>
				<view class="articleImage" @click="articleNav(item[1])">
					<image
						:src="item[5]"
						mode="widthFix"></image>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				newLikeList:{},//最新点赞
				LikeList:{}//最近20条
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
				url: getApp().globalData.BaseUrl+"/Article/getUserInformation",
				success: function(res) {
					
					that.newLikeList=res.data.data
					// console.log(res.data.data)
				}
			})
			uni.request({
				data: {
					userKey: getApp().globalData.userKey,
					type:"点赞"
				},
				method: "GET",
				url: getApp().globalData.BaseUrl+"/Article/getUserInformation20",
				success: function(res) {
					
					that.LikeList=res.data.data
					// console.log(res.data.data)
				}
			})
			uni.hideLoading();
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			articleNav(id){
				// console.log(id)
				uni.navigateTo({
					url: "../../../comment/comment?id=" + id
				});
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
		width: 90%;
		background-color: rgba(255, 255, 255, 0.2);
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
		width: 100%;
		margin-top: 20rpx;
		display: flex;
		justify-content: space-between;
		position: fixed;
		top: -20rpx;
		padding-left: 40rpx;
		padding-bottom: 20rpx;
	}

	.titleTop .back {
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
		margin-right: 80rpx;
	}

	.hint {
		margin-top: 180rpx;
		color: gray;
		margin-left: 45rpx;
		margin-bottom: 30rpx;
		font-size: 24rpx;
	}

	.content {
		display: flex;
		background-color: #212121;
		padding-top: 30rpx;
		margin-top: 15rpx;
		padding-bottom: 10rpx;
	}

	.content .title {
		margin: 20rpx;
		margin-top: 5rpx;
		font-size: 26rpx;
		width: 300rpx;
		height: 80rpx;
		overflow: hidden;
	}

	.content .image image {
		width: 80rpx;
		border-radius: 50%;
		margin-left: 40rpx;
	}

	.articleImage {
		overflow: hidden;
		height: 80rpx;
		width: 250rpx;
		border-radius: 20rpx;
	}

	.articleImage image {
		width: 250rpx;
	}
</style>
