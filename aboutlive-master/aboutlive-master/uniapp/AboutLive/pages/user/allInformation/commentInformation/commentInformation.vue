<template>
	<view>
		<view class="titleTop">
			<view class="back" @click="back()">
				<image src="../../../../static/back.png" mode="widthFix"></image>
			</view>
			<view class="title">
				回复我的
			</view>
			<view style="width: 50rpx;"></view>
		</view>
		
		<view class="hint">
			最新
		</view>
		
		<view v-if="newCommentList==''" style="color: gray;font-size: 24rpx;width: 100%;text-align: center;">
			暂无最新的点赞信息
		</view>
		
		<view class="content" v-for="(item,index) in newCommentList" :key="index" @click="articleNav(item[1])">
			<view class="head">
				<image :src="item[2]" mode="widthFix"></image>
			</view>
			<view>
				<view style="display: flex;">
					<view style="color: #fff;margin-left: 20rpx;">
						{{item[3]}}
					</view>
					<view class="font">
						{{item[0]}}
					</view>
				</view>
				<view class="commentContent">
					点击进入文章查看内容。
				</view>
			</view>
			<view style="color: #fff;display: flex;">
				<view>
					回复
				</view>
				<view style="color: #CD3333;margin-left: 20rpx;">
					举报
				</view>
			</view>
		</view>
		
		<view class="hint" style="margin-top: 35rpx;">
			近二十条
		</view>
		
		<view v-if="commentList==''" style="color: gray;font-size: 24rpx;width: 100%;text-align: center;">
			暂无点赞信息
		</view>
		
		<view class="content" v-for="(item,index) in commentList" :key="index" @click="articleNav(item[1])">
			<view class="head">
				<image :src="item[2]" mode="widthFix"></image>
			</view>
			<view>
				<view style="display: flex;">
					<view style="color: #fff;margin-left: 20rpx;">
						{{item[3]}}
					</view>
					<view class="font">
						{{item[0]}}
					</view>
				</view>
				<view class="commentContent">
					点击进入文章查看内容。
				</view>
			</view>
			<view style="color: #fff;display: flex;">
				<view>
					回复
				</view>
				<view style="color: #CD3333;margin-left: 20rpx;">
					举报
				</view>
			</view>
		</view>
		
		<view style="height: 150rpx;">
			
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				newCommentList:{},//未读消息
				commentList:{}//已读的20条
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
					type:"评论"
				},
				method: "GET",
				url: getApp().globalData.BaseUrl+"/Article/getUserInformation",
				success: function(res) {
					
					that.newCommentList=res.data.data
					console.log(res.data.data)
				}
			})
			uni.request({
				data: {
					userKey: getApp().globalData.userKey,
					type:"评论"
				},
				method: "GET",
				url: getApp().globalData.BaseUrl+"/Article/getUserInformation20",
				success: function(res) {
					
					that.commentList=res.data.data
					console.log(res.data.data)
					uni.hideLoading();
				}
			})
			
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
		
		.hint {
			margin-top: 180rpx;
			color: gray;
			margin-left: 45rpx;
			margin-bottom: 30rpx;
			font-size: 24rpx;
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
		.content{
			padding: 20rpx 0;
			background-color: #212121;
			display: flex;
		}
		.content .head image{
			width: 70rpx;
			margin-left: 40rpx;
			border-radius: 50%;
		}
		.content .font{
			 color: gray;
			 margin-left: 20rpx;
			 font-size: 22rpx;
		}
		.commentContent{
			color: #fff;
			width: 450rpx;
			margin-left: 20rpx;
			margin-top: 5rpx;
			font-size: 22rpx;
		}
</style>
