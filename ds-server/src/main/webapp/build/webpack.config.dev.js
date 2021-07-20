const { merge } = require('webpack-merge')
const common = require('./webpack.config.common')
const proxy = require('./proxy_dev')

const env = require('./env')

module.exports = envParams => {
  const envToUse = Object.assign({
    CONTEXT_PATH: '/',
    entry: 'src'
  }, env, envParams)
  return merge(common(envToUse), {
    mode: 'development',
    module: {
      rules: [{
        test: /\.css$/,
        use: [
          'style-loader',
          'css-loader'
        ]
      }, {
        test: /\.less$/,
        use: [
          'style-loader',
          'css-loader',
          'less-loader'
        ]
      }]
    },
    devServer: {
      publicPath: envToUse.CONTEXT_PATH,
      contentBase: `.${envToUse.CONTEXT_PATH}`,
      overlay: {
        warnings: false,
        errors: true
      },
      // 将这个改到一个不存在的地方，才能触发后台校验
      index: 'index.html',
      host: '0.0.0.0',
      port: 80,
      proxy: proxy(envToUse),
      historyApiFallback: {
        rewrites: [{
          from: new RegExp(`^${envToUse.CONTEXT_PATH}`),
          to: envToUse.CONTEXT_PATH
        }]
      }
    }
  })
}
