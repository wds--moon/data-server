const { merge } = require('webpack-merge')
const path = require('path')
const common = require('./webpack.config.common')
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin')
const MiniCssExtractPlugin = require('mini-css-extract-plugin')
const CompressionPlugin = require('compression-webpack-plugin')
const { CleanWebpackPlugin } = require('clean-webpack-plugin')
const env = require('./env')
module.exports = envParams => {
  const envToUse = Object.assign({
    CONTEXT_PATH: '/',
    entry: 'src'
  }, env, envParams)
  return merge(common(envToUse), {
    mode: 'production',
    module: {
      rules: [{
        test: /\.css$/,
        use: [
          MiniCssExtractPlugin.loader,
          'css-loader'
        ]
      }, {
        test: /\.less$/,
        use: [
          MiniCssExtractPlugin.loader,
          'css-loader',
          'less-loader'
        ]
      }]
    },
    optimization: {
      minimizer: ['...', new CssMinimizerPlugin()],
    },
    plugins: [
      new CleanWebpackPlugin(),
      new MiniCssExtractPlugin({
        chunkFilename: 'static/styles/[id].[contenthash:7].css',
        filename: 'static/styles/[id].[contenthash:7].css'
      }),
      new CompressionPlugin({
        test: /\.(js|css|html)$/
      })
    ],
    target: ['web', 'es5'],
    output: {
      path: path.resolve(__dirname, `..${envToUse.CONTEXT_PATH}`),
      publicPath: envToUse.CONTEXT_PATH,
      chunkFilename: 'static/js/[id].[contenthash:7].js',
      filename: 'static/js/[id].[contenthash:7].js'
    }
  })
}
