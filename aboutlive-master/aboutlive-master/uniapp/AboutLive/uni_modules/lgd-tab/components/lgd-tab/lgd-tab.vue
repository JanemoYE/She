<template>
	<view>
		<view class="tabview">
			<scroll-view class="scrollView" scroll-x show-scrollbar="false" :scroll-left="scrollLeft" scroll-with-animation style="height: 74rpx;">
				<view class="tabBox" :style="{ 'justify-content': isOutWindow ? '' : 'space-around'}">
					<view class="items" v-for="(item, index) in tabValue" :key="index" @click="clickTab(index)">
						<view class="tabText" :class="index == tIndex ? 'active' : ''" :style="{ 'font-size': fontSize + 'px', color: index == tIndex ? textColor : ''}">
							{{item}}
						</view>
					</view>
				</view>
				<view class="underscore" :style="{ width: inderWidth + 'px', 'margin-left': indexLeft + boxLeft + 'px', 'background-color': underlineColor }" />
			</scroll-view>
		</view>
		<view class="gap" style="height: 74rpx;" :style="{'background-color': background }" />
	</view>
</template>

<script>
	export default {
		props: {
			tabValue: { // tab数据
				type: Array,
				default: [],
				required: true
			},
			textColor: { // 颜色
				type: String,
				default: '#333'
			},
			underlineColor: {
				type: String,
				default: '#212121'
			},
			fontSize: { // 字体大小
				type: Number,
				default: 16
			},
			background: {
				type: String,
				default: '#f4f4f4'
			},
			// 传入tab下标
			tabIndex: {
				type: Number,
				default: 0
			}
		},
		data() {
			return {
				windowsWidth: 0, // 设备窗口大小
				boxLeft: 0,
				isOutWindow: false, // tab是否超出屏幕
				inderWidth: 0, // 下划线宽度
				indexLeft: 0, // 下划线距左边距离
				scrollLeft: 0,
				tIndex: 0, // tab下标
			};
		},
		watch: {
			// 监听传入下标更改
			tabIndex(newValue, oldValue) {
				this.clickTab(newValue)
			}
		},
		methods: {
			clickTab(index) {
				// 更改选中下标
				this.tIndex = index
				this.$emit("getIndex", index)
				// 获取盒子移动距离
				if (this.isOutWindow && index >= 0) {
					uni.createSelectorQuery().in(this).select(".tabBox").boundingClientRect().exec((data) => {
						if (index == 0) {
							this.boxLeft = 0
						} else {
							// 移动距离
							this.boxLeft = -data[0].left
						}
					})
				}
				uni.createSelectorQuery().in(this).selectAll(".items").boundingClientRect().exec((data) => {
					let width = data[0][index].width
					let left = data[0][index].left
					let newLeft = 0

					// tab超出屏幕
					if (this.isOutWindow) {
						if (index == 0) {
							left = 0
						}

						// 防止移动后距离缩短问题
						for (let i = 0; i < index; i++) {
							newLeft += data[0][i].width
						}

						// 是否需要居中
						if (this.windowsWidth / 2 < newLeft + width) {
							this.scrollLeft = width / 2 - (this.windowsWidth / 2 - newLeft)
						} else {
							this.scrollLeft = 0
						}
					}
					// 点击tab宽度
					this.inderWidth = width / 2
					// 移动距离
					this.indexLeft = (width - width / 2) / 2 + left
				})
			}
		},
		created() {
			let that = this;
			// 获取屏幕宽度
			uni.getSystemInfo({
				success(res) {
					that.windowsWidth = res.windowWidth
				}
			})
		},
		mounted() {
			// 判断tab是否超出屏幕
			uni.createSelectorQuery().in(this).selectAll(".items").boundingClientRect().exec((data) => {
				if (data[0][this.tabValue.length - 1].right > this.windowsWidth) {
					this.isOutWindow = true
				}
				this.$nextTick(() => {
					this.clickTab(this.tabIndex)
				})
			})
		}
	}
</script>

<style>
	.tabview {
		width: 100vw;
		position: absolute;
		left: 0;
	}

	.scrollView {
		width: 100%;
		white-space: nowrap;
	}

	.tabBox {
		display: flex;
		align-items: center;
	}

	.items {
		line-height: 60rpx;
		padding: 0 20rpx;
	}

	.tabText {
		color: #666666;
	}

	.active {
		font-weight: bold;
	}

	.underscore {
		height: 3px;
		transition: .2s all;
		border-radius: 6rpx;
	}

	/deep/.uni-scroll-view::-webkit-scrollbar {
		display: none
	}
</style>
