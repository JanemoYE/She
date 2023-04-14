<template>
	<view class="content">

		<!-- 评论表单 -->
		<view class="commentFrom" v-if="from=='on'">

			<view style="position: absolute;left: 580rpx;font-size: 45rpx;font-family: '黑体';" @click="delFrom()">x
			</view>
			<view class="hint">
				评论/回复
			</view>

			<view class="input" style="margin-left: 50rpx;">
				<input type="text" @input="userContentInput($event)" :value="userContent" placeholder="请留下您的评论..."
					style="font-size: 26rpx;" />
			</view>
			<view class="sumbit" @click="addComment()">
				发布
			</view>

			<view class="hint" style="text-align: left;margin-bottom: 40rpx;">
				回复用户 :
			</view>

			<view class="replyUser">
				<view v-if="replyUser==0">暂无回复用户</view>
				<view v-if="replyUser!=0" class="userComment">
					<view class="image">
						<image :src="commentList[replyUser-1][0]" mode="widthFix"></image>
					</view>
					<view>
						<view class="name">
							{{commentList[replyUser-1][1]}}
						</view>
						<view class="CommentContent">
							{{commentList[replyUser-1][2]}}
						</view>
					</view>
				</view>

			</view>
		</view>
		<!-- 评论表单 -->

		<view class="article" :style="'background: url('+author[0]+');'">
			<image :src="author[0]" mode="widthFix"></image>
		</view>
		<view style="margin: -450rpx;text-align: center;opacity: 0.95;">
			<image style="border-radius: 40rpx;width: 650rpx;" :src="author[0]" mode="widthFix"></image>
		</view>

		<view class="user" style="margin-top: 600rpx;">
			<view class="images">
				<image :src="author[4]" mode="widthFix"></image>
			</view>
			<view style="width: 550rpx;">
				<view class="name">
					{{author[3]}}
				</view>
				<view class="lable">
					作者
				</view>
			</view>
			<view class="time1">
				{{author[1]}}
			</view>
		</view>

		<view class="likeNumber1">
			<view style="font-size: 26rpx;width: 60rpx;">
				收藏
			</view>
			<view class="like" id="box" :style="likeNow==0?'display: block;':'display: none;'" @click="like()">
				<!--已收藏-->
			</view>
			<view class="like" id="box1" :style="likeNow==1?'display: block;':'display: none;'" @click="like()">
				<!--未收藏-->
			</view>
		</view>



		<view style="display: flex;">
			<view class="comment" style="margin-top: 50rpx;display: flex;">
				评论
				<view style="font-size: 28rpx;margin-top: 5rpx;margin-left: 10rpx;">
					{{commentCount}}
				</view>
			</view>
			<view class="goCommentBtn" @click="goCommentBtn()">
				前往评论
			</view>
		</view>

		<view class="hr"></view>

		<view v-for="(item,index) in commentList" :key="index">
			<view class="user">
				<view class="images" @click="userHome(item[5])">
					<image :src="item[0]" mode="widthFix"></image>
				</view>
				<view style="width: 550rpx;">
					<view class="name">
						{{item[1]}}
					</view>
					<view style="font-weight: bold;text-align: center;">
						<view :class="'lv Exe'+item[4]">
							<image src="../../static/lv.png" mode="widthFix"></image>
							<view>
								Lv-{{item[4]}}
							</view>
						</view>
					</view>
				</view>
				<view style="margin-left: 200rpx;">
					<view style="margin-top: 0rpx;margin-left: 205rpx;margin-bottom: 5rpx;color: red;">
						<view v-if="item[5]==userKey" @click="delComment(item[6])">删除</view>
					</view>
					<view class="time1" style="margin-top: 0rpx;">
						{{item[3]}}
					</view>
				</view>
			</view>
			<view style="display: flex;">
				<view class="userCommentContent">
					{{item[2]}}
				</view>
				<view style="color: #0057b4;margin: 15rpx;" @click="reply(index)">
					回复
				</view>
			</view>

			<view class="replyUserComment" v-if="item[7]!=0">
				<view class="user">
					<view class="images" @click="userHome(replyList[IndexCorrespondId.get(item[7])][5])">
						<image :src="replyList[IndexCorrespondId.get(item[7])][0]" mode="widthFix"
							style="width: 50rpx;"></image>
					</view>
					<view style="width: 550rpx; font-size: 20rpx;">
						<view class="name">
							{{replyList[IndexCorrespondId.get(item[7])][1]}}
						</view>
						<view style="font-weight: bold;text-align: center;">
							<view :class="'lv Exe'+replyList[IndexCorrespondId.get(item[7])][4]"
								style="height: 30rpx;width: 25rpx;">
								<image src="../../static/lv.png" mode="widthFix"
									style="width: 20rpx;margin-top: 5rpx;margin-left: 2rpx;"></image>

							</view>
						</view>
					</view>
				</view>
				<view style="display: flex;">
					<view class="userCommentContent" style="font-size: 20rpx;">
						{{replyList[IndexCorrespondId.get(item[7])][2]}}
					</view>
				</view>
			</view>

			<!-- <view class="hr1" style="margin-top: 25rpx;margin-bottom: 25rpx;"></view> -->
			<view class="hr1" style="margin-top: 25rpx;margin-bottom: 25rpx;"></view>
		</view>

		<view style="margin-top: 100rpx;height: 200rpx;"></view>

		<view class="button" @click="back()">
			返回
		</view>
	</view>
</template>

<script>
	var map = new Map(); //存放index对应的id

	export default {
		data() {
			return {
				id: "",
				commentCount: 0,
				page: 1,
				commentList: [],
				replyList: [],
				likeNow: 0,
				author: {},
				userKey: "",
				userContent: "",
				replyUser: 0,
				from: "off",
				time: "",
				IndexCorrespondId: {},
				i: "" //存储删除的id
			}
		},

		onReachBottom() {

			if (this.commentList.length < this.commentCount) {
				this.page = this.page + 1;
				var that = this;
				uni.showLoading({
					title: '加载中...'
				});
				// 查询评论内容
				uni.request({
					data: {
						page: that.page,
						limit: 10,
						id: that.id
					},
					method: "GET",
					url: getApp().globalData.BaseUrl + "/Article/comment",
					success: function(res) {
						console.log(res.data.data)
						for (var j = ((that.page - 1) * 10); j < ((that.page - 1) * 10) + res.data.data
							.length; j++) {
							that.commentList[j] = res.data.data[j - ((that.page - 1) * 10)]
						}
						that.replyList = that.commentList

						for (var t = ((that.page - 1) * 10); t < ((that.page - 1) * 10) + res.data.data
							.length; t++) {
							console.log("t=" + t)
							console.log("res.data.data[t-(that.page-1*10)][6] = " + res.data.data[t - ((that
								.page - 1) * 10)][6])
							map.set(res.data.data[t - ((that.page - 1) * 10)][6], t);
						}
						that.IndexCorrespondId = map;

						// console.log(res.data.data)
						uni.hideLoading();
					}
				})
			} else {
				uni.showToast({
					title: '暂无更多评论',
					icon: 'none',
					duration: 2000
				});
			}

		},

		onPullDownRefresh() {
			this.page=1
			var that = this;
			map=new Map();
			that.commentList = {}
			that.replyList = {}
			
			// 查询评论内容
			uni.request({
				data: {
					page: that.page,
					limit: 10,
					id: that.id
				},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Article/comment",
				success: function(res) {
					that.commentList = res.data.data
					that.replyList = res.data.data
					for (var j = 0; j < res.data.data.length; j++) {
						map.set(res.data.data[j][6], j);
					}
					that.IndexCorrespondId = map;
					// 获取评论的总数量
					uni.request({
						data: {
							id: that.id
						},
						method: "GET",
						url: getApp().globalData.BaseUrl + "/Article/commentArticleIdCount",
						success: function(res) {
							that.commentCount = res.data.data
							uni.stopPullDownRefresh();
						}
					})
				}
			})

		},

		onLoad(openid) {
			map = new Map(); //存放index对应的id
			uni.getStorage({
				key: "userKey",
				success: function(res) {
			 	getApp().globalData.userKey = JSON.parse(res.data)
				}
			})
			uni.getStorage({
				key: "userArrays",
			 success: function(res) {
					getApp().globalData.userArrays = JSON.parse(res.data)
				}
			})

			if (getApp().globalData.userKey == "") {
				uni.reLaunch({
					url: "../login/login"
				})
			}

			var that = this;
			this.id = openid.id
			this.userKey = getApp().globalData.userKey
			uni.showLoading({
				title: '加载中...'
			});

			// 查询文章作者内容和文章图片
			uni.request({
				data: {
					id: that.id
				},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Article/articleComment",
				success: function(res) {
					that.author = res.data.data
					// 获取评论的总数量
					uni.request({
						data: {
							id: that.id
						},
						method: "GET",
						url: getApp().globalData.BaseUrl + "/Article/commentArticleIdCount",
						success: function(res) {
							that.commentCount = res.data.data
							// console.log(res.data.data)
						}
					})
				}
			})

			// 查询用户是否已经收藏
			uni.request({
				data: {
					userKey: getApp().globalData.userKey,
					articleId: that.id
				},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Article/articleLikeTrue",
				success: function(res) {
					if (res.data.code > 0) {
						that.likeNow = 0;
					} else {
						that.likeNow = 1;
					}
				}
			})
			// 查询评论内容
			uni.request({
				data: {
					page: that.page,
					limit: 10,
					id: that.id
				},
				method: "GET",
				url: getApp().globalData.BaseUrl + "/Article/comment",
				success: function(res) {
					that.commentList = res.data.data
					that.replyList = res.data.data
					for (var j = 0; j < res.data.data.length; j++) {
						map.set(res.data.data[j][6], j);
					}
					that.IndexCorrespondId = map;
					console.log(res.data.data)
				}
			})

			uni.hideLoading();
		},
		methods: {
			userContentInput(value) {
				this.userContent = value.detail.value;
			},
			// 获取当前日期与时间
			nowTime() {
				var date = new Date(); //Fri Oct 29 2021 16:37:56 GMT+0800 (CST)
				var timestamp = new Date().getTime(); //1635496676223		(毫秒级)
				// var y= date.getFullYear(); //获取完整的年份(4位)
				// var m= date.getMonth(); //获取当前月份(0-11,0代表1月)
				// var d= date.getDate(); //获取当前日(1-31)
				// var w= date.getDay(); //获取当前星期X(0-6,0代表星期天)
				// var h= date.getHours(); //获取当前小时数(0-23)
				// var i= date.getMinutes(); //获取当前分钟数(0-59)
				// var s= date.getSeconds(); //获取当前秒数(0-59)
				this.time = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date
					.getHours() + ":" + date.getMinutes();
			},
			// 评论
			addComment() {
				// console.log(this.commentList[this.replyUser-1][6])
				this.nowTime()
				var that = this;
				if (this.userContent == "") {
					uni.showToast({
						title: '请填写评论内容',
						icon: 'none',
						duration: 2000
					});
				} else {
					if (this.replyUser == 0) {

						uni.request({
							data: {
								articleId: that.id,
								userKey: getApp().globalData.userKey,
								userContent: that.userContent,
								time: that.time
							},
							method: "GET",
							url: getApp().globalData.BaseUrl + "/Article/addComment",
							success: function(res) {
								if (res.data.code == 0) {
									that.delFrom()
									uni.showToast({
										title: '评论成功！',
										icon: 'none',
										duration: 3000
									});
								} else {
									uni.showToast({
										title: '评论失败啦！',
										icon: 'none',
										duration: 2000
									});
								}

							}
						})


					} else {
						console.log("replyUser= " + that.replyUser)
						console.log("commentList= " + that.commentList[that.replyUser - 1][6])
						uni.request({
							data: {
								articleId: that.id,
								userContent: that.userContent,
								userKey: getApp().globalData.userKey,
								replyUser: that.commentList[that.replyUser - 1][6],
								time: that.time
							},
							method: "GET",
							url: getApp().globalData.BaseUrl + "/Article/addReplyComment",
							success: function(res) {
								if (res.data.code == 0) {
									that.delFrom()
									uni.showToast({
										title: '回复成功！',
										icon: 'none',
										duration: 3000
									});
								} else {
									uni.showToast({
										title: '回复失败啦！',
										icon: 'none',
										duration: 2000
									});
								}

							}
						})



					}
				}
			},

			goCommentBtn() {
				this.from = "on";
			},

			reply(index) { //回复的用户
				console.log(index)
				this.replyUser = (index + 1);
				this.from = "on";
			},
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			userHome(userKey) {
				uni.navigateTo({
					url: "../userHome/userHome?userKey=" + userKey
				});
			},
			delComment(i) {
				// console.log(i)
				var that = this;
				this.i = i;
				uni.request({
					header: {
						'Content-Type': 'application/x-www-form-urlencoded'
					},
					data: {
						userKey: getApp().globalData.userKey,
						id: that.i
					},
					method: "DELETE",
					url: getApp().globalData.BaseUrl + "/Article/delComment",
					success: function(res) {
						// console.log(res)
						if (res.data.code == 0) {
							uni.showToast({
								title: '删除成功！',
								icon: 'none',
								duration: 2000
							});
						} else {
							uni.showToast({
								title: '删除失败！',
								icon: 'error',
								duration: 2000
							});
						}
					},
				})
			},

			like() {
				var that = this;
				if (this.likeNow == 0) {
					uni.request({
						header: {
							'Content-Type': 'application/x-www-form-urlencoded'
						},
						data: {
							userKey: getApp().globalData.userKey,
							articleId: that.id
						},
						method: "POST",
						url: getApp().globalData.BaseUrl + "/Article/addLikeArticle",
						success: function(res) {
							console.log(res)
							if (res.data.code == 0) {
								that.likeNow = 1;
								uni.showToast({
									title: '收藏成功！',
									icon: 'none',
									duration: 2000
								});
							} else {
								uni.showToast({
									title: '收藏失败啦！',
									icon: 'none',
									duration: 2000
								});
							}
						},
					})
				} else {
					uni.request({
						data: {
							userKey: getApp().globalData.userKey,
							articleId: that.id
						},
						method: "GET",
						url: getApp().globalData.BaseUrl + "/Article/delLikeArticle",
						success: function(res) {
							if (res.data.code == 0) {
								that.likeNow = 0;
								uni.showToast({
									title: '取消收藏成功！',
									icon: 'none',
									duration: 2000
								});
							} else {
								uni.showToast({
									title: '取消收藏失败啦！',
									icon: 'none',
									duration: 2000
								});
							}
						}
					})
				}
			},

			delFrom() {
				this.replyUser = 0;
				this.userContent = "";
				this.from = "off";
			}

		}
	}
</script>

<style>
	page {
		font-size: 26rpx;
	}

	.article {
		width: 100%;
		height: 500rpx;
		margin-top: 50rpx;
		border-radius: 50rpx;
		filter: blur(10px);
	}

	.button {
		color: #fff;
		font-size: 32rpx;
		text-align: center;
		position: fixed;
		background-color: #000000;
		width: 90%;
		border-radius: 50rpx;
		height: 50rpx;
		padding: 20rpx;
		left: 18rpx;
		bottom: 100rpx;
	}

	.goCommentBtn {
		background-color: #212121;
		width: 150rpx;
		height: 50rpx;
		text-align: center;
		margin-left: 40rpx;
		border-radius: 25rpx;
		line-height: 50rpx;
		color: #fff;
		margin-top: 50rpx;
	}

	.hr {
		width: 100rpx;
		background-color: #212121;
		height: 8rpx;
		border-radius: 30rpx;
		margin-top: 10rpx;
		margin-left: 40rpx;
		margin-bottom: 30rpx;
	}

	.commentFrom {
		background-color: #fff;
		border: gray solid 1px;
		width: 80%;
		padding: 20rpx;
		border-radius: 30rpx;
		position: fixed;
		left: 50rpx;
		margin-top: 300rpx;
	}

	.hr1 {
		width: 80%;
		background-color: rgba(0, 0, 0, 0.1);
		height: 1rpx;
		border-radius: 30rpx;
		margin: 0 auto;
		margin-top: 50rpx;
		margin-bottom: 50rpx;
	}

	.comment {
		margin-left: 40rpx;
		font-size: 32rpx;
		/* font-weight: bold; */
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
		margin-top: -5rpx;
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
		margin-top: 45rpx;
		margin-left: 10rpx;
		width: 500rpx;
	}

	.userCommentContent {
		margin-left: 40rpx;
		font-size: 28rpx;
		margin-top: 10rpx;
		width: 80%;
	}

	.likeNumber1 {
		display: flex;
		margin-left: 600rpx;
	}

	.likeNumber1 .like image {
		width: 40rpx;
	}

	.likeNumber1 .number {
		font-size: 26rpx;
		margin-top: 5rpx;
		margin-right: 5rpx;
		color: rgba(0, 0, 0, 0.6);
	}

	#box {
		width: 40rpx;
		height: 40rpx;
		background: url(../../static/like.png)no-repeat;
		background-position: left;
		background-size: cover;
	}

	#box1 {
		width: 40rpx;
		height: 40rpx;
		background: url(../../static/likes.png)no-repeat;
		background-position: left;
		background-size: cover;
	}

	.commentFrom {
		z-index: 2;
		background-color: rgba(0, 0, 0, 0.83);
		color: #fff;
	}

	.commentFrom .hint {
		text-align: center;
		margin: 20rpx;
	}

	.commentFrom .sumbit {
		background-color: #fff;
		padding: 10rpx;
		width: 120rpx;
		text-align: center;
		color: #000;
		border-radius: 20rpx;
		margin-left: 450rpx;
		margin-top: 20rpx;
		margin-bottom: 20rpx;
	}

	.commentFrom .input {
		border-left: #fff dashed 1px;
		padding-left: 10rpx;
	}

	.commentFrom .replyUser {
		margin-left: 50rpx;
		margin-top: 10rpx;
		margin-bottom: 30rpx;
	}

	.replyUser .userComment {
		display: flex;
	}

	.replyUser .userComment .image image {
		width: 80rpx;
		border-radius: 50%;
	}

	.replyUser .userComment .name {
		margin-left: 20rpx;
	}

	.replyUser .CommentContent {
		padding: 15rpx;
		border-radius: 15rpx;
		width: 410rpx;
		margin-left: 5rpx;
	}

	.replyUserComment {
		margin-left: 100rpx;
		margin-top: 20rpx;
	}
</style>
