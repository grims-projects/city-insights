import type { Config } from 'tailwindcss'

const config: Config = {
  content: [
    './pages/**/*.{js,ts,jsx,tsx,mdx}',
    './components/**/*.{js,ts,jsx,tsx,mdx}',
    './app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      colors: {
          'black': '#323232',
          'off-white': '#F2ECFF',
          'gray': '#D9D9D9',
          'light-gray': '#636363',
          'green': '#6E9290',
          'light-green': '#8BA7A6',
          'red': '#BE3C63',
          'dark-red': '#9B3151'
      }
    },
  },
  plugins: [],
}
export default config
