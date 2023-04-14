<template>
	<view>
		<view class="titleTop">
			<view class="back" @click="back()">
				<image src="../../static/back.png" mode="widthFix"></image>
			</view>
		</view>

		<view class="userHead1" :style="'background: url('+outhors[3]+');'">

		</view>
		<view class="userHead">
			<view class="">
				<image :src="outhors[3]" mode="widthFix"></image>
			</view>
			<br>
		</view>

		<view class="nameTitle">
			- {{outhors[4]}} -
		</view>

		<view class="myFieryList" @click="hint()">
			
				<view class="type">
					<view style="font-weight: bold;text-align: center;">
						{{outhors[0]}}
					</view>
					<view style="font-size: 26rpx;text-align: center;color: gray;">
						关注
					</view>
				</view>

				<view class="type"
					style="border-right: rgba(0,0,0,0.15) solid 1px;width: 250rpx;border-left: rgba(0,0,0,0.15) solid 1px;">
					<view style="font-weight: bold;text-align: center;">
						{{outhors[1]}}
					</view>
					<view style="font-size: 26rpx;text-align: center;color: gray;">
						粉丝
					</view>
				</view>

			<view class="type">
				<view style="font-weight: bold;text-align: center;">
					<view class="lv" :class="'Exe'+1">
						<image src="../../static/lv.png" mode="widthFix"></image>
						<view>
							Lv-{{outhors[2]}}
						</view>
					</view>
				</view>
				<view style="font-size: 26rpx;text-align: center;color: gray;">
					声望
				</view>
			</view>
		</view>

		<view class="typeTar">
			<view :class="tarIndex==0? 'typeTarSelected':''" class="typeTar" style="margin-right: 40rpx;"
				@click="tarIndexClick(0)">
				句子
			</view>
			<view :class="tarIndex==1? 'typeTarSelected':''" class="typeTar" @click="tarIndexClick(1)">
				评论
			</view>
		</view>

		<view v-if="tarIndex==0">
			<view v-if="userAllArticleList.length>0">
				<view v-for="(userAllArticleList,index) in userAllArticleList" :key="index">
					<view class="bg" @click="articleUrl(userAllArticleList[3])">
						<view class="image">
							<image
								:src="userAllArticleList[0]"
								mode="widthFix"></image>
						</view>
						<view class="font">
							{{userAllArticleList[2]}}
						</view>
						<view
							style="color: gray;font-size: 28rpx; text-align: right;width: 90%;margin: 0 auto;margin-top: 30rpx;">
							{{userAllArticleList[1]}}
						</view>
						<view class="list">
							<view class="like">
								<view><image src="../../static/likes.png" mode="widthFix"></image></view>
								<view style="font-size: 26rpx;color: gray;">{{userAllArticleList[4]}}</view>
							</view>
							<view class="comment">
								<view><image src="../../static/comment.png" mode="widthFix"></image></view>
								<view style="font-size: 26rpx;color: gray;">{{userAllArticleList[5]}}</view>
							</view>
						</view>
					</view>
					
					<view class="hr"></view>
				</view>
			</view>
			
			<view v-else>
				<view class="imageNull">
					<image src="../../static/isNull.png" mode="widthFix"></image>
				</view>
				<view style="text-align: center;font-size: 28rpx;color: gray;margin: 30rpx;">
					该用户还未发布
				</view>
			</view>
		</view>

		<view v-if="tarIndex==1">
			<view v-for="(commentList,index) in commentList" :key="index">
				<view style="margin-top: 100rpx;"></view>
				<view class="user">
					<view class="images">
						<image :src="commentList[0]" mode="widthFix"></image>
					</view>
					<view>
						<view class="name">
							{{commentList[1]}}
						</view>
						<view class="time1">
							{{commentList[3]}}
						</view>
					</view>
				</view>
				<view class="userCommentContent">
					<text @click="comment(commentList[4])" style="color: #007aff;">#点击查看文章#</text>
					{{commentList[2]}}
				</view>
			</view>
		</view>

		<view style="margin-top: 200rpx;height: 10rpx;"></view>
		<view class="fan" @click="addfoucs()" v-if="foucsTrue=='false'">关注</view>
		<view class="fan" style="background-color: #C8C7CC;" @click="delfoucs()" v-if="foucsTrue=='true'">取消关注</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				tarIndex: 0,
				commentList: [],
				othersUserKey: "",
				outhors: {},
				foucsTrue:"",
				userAllArticleList:[]
			}
		},
		onLoad(openid) {
			this.othersUserKey = openid.userKey
			uni.showLoading({
				title: '加载中...'
			});
			var that = this;

			uni.request({
				data: {
					userKey: this.othersUserKey
				},
				method: "GET",
				url: getApp().globalData.BaseUrl+"/Article/userAllArticle",
				success: function(res) {
					if (res.data.code == 0) {
						that.userAllArticleList = res.data.data
						// console.log(res.data.data)
					} else {
						uni.showToast({
							title: '好像什么都没查到呢...',
							icon: 'none',
							duration: 2000
						});
					}
			
				}
			})

			uni.request({
				data: {
					othersUserKey: this.othersUserKey,
					myUserKey:getApp().globalData.userKey
				},
				method: "GET",
				url: getApp().globalData.BaseUrl+"/User/outhorsFiery",
				success: function(res) {
					if (res.data.code == 0) {
						that.outhors = res.data.data
						that.foucsTrue=that.outhors[5];
						// console.log(that.outhors)
					} else {
						uni.showToast({
							title: '好像没有查到什么呢...',
							icon: 'none',
							duration: 2000
						});
					}

				}
			})
			
			uni.request({
				data: {
					userKey: this.othersUserKey
				},
				method: "GET",
				url: getApp().globalData.BaseUrl+"/Article/userAllComment",
				success: function(res) {
					if (res.data.code == 0) {
						that.commentList = res.data.data
						// console.log(that.commentList)
					} else {
						uni.showToast({
							title: '好像没有查到什么呢...',
							icon: 'none',
							duration: 2000
						});
					}

				}
			})
			uni.hideLoading();
		},
		methods: {
			topBack() {
				uni.pageScrollTo({
					scrollTop: 0, // 滚动到页面的目标位置  这个是滚动到顶部, 0 
					duration: 1000 // 滚动动画的时长
				})
			},
			articleUrl(id){
				uni.navigateTo({
					url: "../comment/comment?id=" + id
				});
			},
			addfoucs() { //关注
				var that = this;
				if(that.othersUserKey!=getApp().globalData.userKey){
					uni.request({
						data: {
							othersUserKey: that.othersUserKey,
							myUserKey: getApp().globalData.userKey
						},
						method: "GET",
						url: getApp().globalData.BaseUrl+"/User/addFocus",
						success: function(res) {
							if (res.data.code == 0) {
								uni.showToast({
									title: '关注成功！',
									icon: 'none',
									duration: 3000
								});
								that.foucsTrue="true"
							} else {
								uni.showToast({
									title: '关注失败！',
									icon: 'none',
									duration: 2000
								});
							}
						}
					})
				}else{
					uni.showToast({
						title: '您不能关注自己！',
						icon: 'none',
						duration: 3000
					});
				}
			},
			delfoucs(){//取消关注
				var that = this;
				uni.request({
					data: {
						othersUserKey: that.othersUserKey,
						myUserKey: getApp().globalData.userKey
					},
					method: "GET",
					url: getApp().globalData.BaseUrl+"/User/delFocus",
					success: function(res) {
						if (res.data.code == 0) {
							uni.showToast({
								title: '取关成功！',
								icon: 'none',
								duration: 3000
							});
							that.foucsTrue="false"
						} else {
							uni.showToast({
								title: '取关失败！',
								icon: 'none',
								duration: 2000
							});
						}
					}
				})
			},
			comment(id) {
				uni.navigateTo({
					url: "../comment/comment?id=" + id
				});
			},
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			hint() {
				uni.showToast({
					title: '不可查看对方的信息',
					icon: 'none',
					duration: 2000
				});
			},
			tarIndexClick(e) {
				this.tarIndex = e;
				this.topBack();
			}
		}
	}
</script>

<style>
	page{
		font-size: 26rpx;
	}
	.hr {
		width: 80%;
		background-color: rgba(0, 0, 0, 0.1);
		height: 1px;
		margin: 0 auto;
	}
	
	.imageNull{
		width: 90%;
		margin: 0 auto;
		margin-top: 20rpx;
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
		margin-bottom: 50rpx;
	}

	.titleTop .back image {
		width: 40rpx;
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

	.nameTitle {
		font-size: 38rpx;
		margin-top: 120rpx;
		text-align: center;
	}

	.myFieryList {
		display: flex;
		width: 80%;
		margin: 0 auto;
		margin-top: 50rpx;
		justify-content: space-between;
	}

	.fan {
		background-color: #f28500cc;
		width: 80%;
		position: fixed;
		left: 60rpx;
		bottom: 50rpx;
		height: 50rpx;
		padding: 20rpx;
		color: #fff;
		font-size: 36rpx;
		text-align: center;
		line-height: 50rpx;
		font-weight: bold;
		border-radius: 50rpx;

	}

	.bg {
		background-color: #fff;
		width: 90%;
		margin: 0 auto;
		margin-top: 20rpx;
		padding: 50rpx 20rpx;
		border-radius: 20rpx;
	}

	.bg .image {
		border-radius: 20rpx;
		overflow: hidden;
		text-align: center;
	}

	.bg .image image {
		width: 80%;
		border-radius: 20rpx;
	}

	.bg .font {
		color: #212121;
		line-height: 55rpx;
		font-size: 32rpx;
		width: 95%;
		margin: 0 auto;
	}

	.typeTar {
		margin-top: 50rpx;
		display: flex;
		/* justify-content: space-around; */
		margin-left: 30rpx;
		color: gray;
		/* background-color: rgba(0, 0, 0, 0.02); */
		/* padding: 15rpx; */
	}

	.typeTarSelected {
		border-bottom: #212121 solid 6rpx;
		color: #212121;
	}

	.user {
		display: flex;
	}

	.user .images image {
		width: 80rpx;
		border-radius: 50%;
		margin-left: 30rpx;
	}

	.user .name {
		margin-left: 20rpx;
		margin-top: 0rpx;
	}

	.user .lable {
		background-color: #000;
		color: #fff;
		margin-left: 15rpx;
		height: 35rpx;
		font-size: 28rpx;
		border-radius: 20rpx;
		padding: 5rpx 15rpx;
		line-height: 33rpx;
		width: 60rpx;
	}

	.time1 {
		font-size: 26rpx;
		color: gray;
		margin-left: 20rpx;
	}

	.userCommentContent {
		margin-left: 40rpx;
		font-size: 28rpx;
		margin-top: 10rpx;
		width: 90%;
	}
	
	.list{
		width: 100%;
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
