<template>
	<view>
		<view class="titleTop">
			<view class="back" @click="back()">
				<image src="../../../static/back.png" mode="widthFix"></image>
			</view>
			<view class="title">
				NEW
			</view>
			<view class="save"></view>
		</view>
		<view style="height: 120rpx;">
			
		</view>
		<view v-for="(item,index) in list" :key="index">
			<view class="bg" @click="comment(item.id)">
				<view class="image">
					<image :src="item.imagesBase64" mode="widthFix"></image>
				</view>
				<view class="font">
					{{item.content}}
				</view>
				<view style="color: gray;font-size: 28rpx; text-align: right;width: 90%;margin: 0 auto;margin-top: 30rpx;">
					{{item.author}}
				</view>
			</view>
		</view>
		<view v-if="list==''">
			<image src="../../../static/isNull.png" mode="widthFix" style="margin: 200rpx;width: 300rpx;"></image>
			<view style="color: gray;text-align: center;">
				暂无数据
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list:{}
			}
		},
		onLoad() {
			uni.showLoading({
				title: '加载中...'
			});
			// console.log(str)
			var that=this;
			uni.request({
				data: {},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Article/newArticle",
				success: function(res) {
					if (res.data.code == 0) {
						that.list = res.data.data
						// console.log(res.data.data)
					} else {
						uni.showToast({
							title: '好像什么都没查到呢...',
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
				uni.navigateBack({
					delta: 1
				})
			},
			comment(id) {
				uni.navigateTo({
					url: "../../comment/comment?id=" + id
				});
			}
		}
	}
</script>

<style>
	page{
		background-color: #f4f4f4;
		font-size: 24rpx;
	}
	.titleTop {
		z-index: 1;
		padding-top: 80rpx;
		width: 100%;
		display: flex;
		justify-content: space-between;
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
	}

	.titleTop .save {
		width: 70rpx;
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
		border-radius: 20rpx;
		width: 80%;
	}
	.bg .font{
		color: #212121;
		line-height: 55rpx;
		font-size: 32rpx;
		width: 95%;
		margin: 0 auto;
	}
</style>
