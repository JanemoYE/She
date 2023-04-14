<template>
	<view>
		<view class="titleTop">
			<view class="back" @click="back()">
				<image src="../../static/back.png" mode="widthFix"></image>
			</view>
			<view class="title">
				我的收藏
			</view>
			<view class="save"></view>
		</view>
		
		<view v-for="(item,index) in userAllArticleList" :key="index">
			<view class="bg" @click="comment(item[0])">
				<view class="image">
					<image :src="item[1]" mode="widthFix"></image>
				</view>
				<view class="font">
					{{item[2]}}
				</view>
				<view style="color: gray;font-size: 28rpx; text-align: right;width: 90%;margin: 0 auto;margin-top: 30rpx;">
					{{item[3]}}
				</view>
				<view class="list">
					<view class="like">
						<view><image src="../../static/likes.png" mode="widthFix"></image></view>
						<view style="font-size: 26rpx;color: gray;">{{item[4]}}</view>
					</view>
					<view class="comment">
						<view><image src="../../static/comment.png" mode="widthFix"></image></view>
						<view style="font-size: 26rpx;color: gray;">{{item[5]}}</view>
					</view>
				</view>
			</view>
		</view>
		
		<view v-if="userAllArticleList.length==0">
			<view class="imageNull">
				<image src="../../static/isNull.png" mode="widthFix"></image>
			</view>
			<view style="text-align: center;font-size: 28rpx;color: gray;margin: 30rpx;">
				暂无收藏内容
			</view>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				userAllArticleList:[]
			}
		},
		onLoad() {
			uni.showLoading({
				title: '加载中...'
			});
			var that=this;
			uni.request({
				data: {
					userKey:getApp().globalData.userKey
				},
				method: "GET",
				url: getApp().globalData.BaseUrl+"/Article/articleLikeList",
				success: function(res) {
					that.userAllArticleList=res.data.data
					console.log(res.data.data)
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
			comment(id) {
				uni.navigateTo({
					url: "../comment/comment?id="+id
				});
			},
		}
	}
</script>

<style>
	page{
		background-color: #f4f4f4;
		font-size: 26rpx;
	}
	.imageNull{
		width: 90%;
		margin: 0 auto;
		margin-top: 100rpx;
		text-align: center;
	}
	.imageNull image{
		width: 300rpx;
	}
.titleTop {
	padding-top: 60rpx;
		width: 90%;
		margin-top: 20rpx;
		display: flex;
		justify-content: space-between;
		margin-left: 40rpx;
	}

	.titleTop .back image {
		width: 40rpx;
	}

	.titleTop .title {
		/* font-weight: bold; */
	}

	.titleTop .save {
		width: 50rpx;
		margin-top: 6rpx;
	}
	
	.bg{
		background-color: #fff;
		width: 90%;
		margin: 0 auto;
		margin-top: 40rpx;
		padding: 50rpx 20rpx;
		border-radius: 20rpx;
	}
	.bg .image{
		border-radius: 20rpx;
		overflow: hidden;
		text-align: center;
	}
	.bg .image image{
		width: 80%;
		border-radius: 20rpx;
	}
	.bg .font{
		color: #212121;
		line-height: 55rpx;
		font-size: 32rpx;
		width: 95%;
		margin: 0 auto;
	}
	.list{
		width: 90%;
		margin: 0 auto;
		display: flex;
		margin-top: 20rpx;
	}
	.list .like{
		text-align: center;
		width: 100rpx;
	}
	.list .like image{
		width: 50rpx;
	}
	.list .comment{
		text-align: center;
		width: 100rpx;
		margin-left: 20rpx;
	}
	.list .comment image{
		width: 50rpx;
	}
</style>
