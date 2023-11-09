import { Cormorant_Garamond, Outfit } from 'next/font/google'
import './globals.css'

const outfit = Outfit({ weight: ["400"], subsets: ['latin'] });
const garamond = Cormorant_Garamond({ weight: ["400"], subsets: ['latin'] });

export { outfit, garamond };

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body className={outfit.className}>{children}</body>
    </html>
  )
}
