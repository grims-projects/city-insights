"use client";

import Banner from "./components/banner/Banner";
import InputForm from "./components/inputForm/InputForm";
import Navbar from "./components/navbar/Navbar";
import { garamond } from "./layout";

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-between pl-16 pr-16 pt-6 pb-0">
      <Banner />
      <section className={`${garamond.className} text-3xl`}>
        <h1>
          Discover fascinating random facts from around the world at the click of a button!
        </h1>
      </section>
      <InputForm />
      <Navbar />
    </main>
  )
}
