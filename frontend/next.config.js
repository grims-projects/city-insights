/** @type {import('next').NextConfig} */
const nextConfig = {
    images: {
        domains: ['picsum.photos']
    },
    async headers() {
        return [
        {
        source: "/",
          headers: [
         { key: "Access-Control-Allow-Credentials", value: "true" },
         { key: "Access-Control-Allow-Origin", value: "http://localhost:3000" }
        ]
        }
        ]
    }
}

module.exports = nextConfig
