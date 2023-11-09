"use client";
import Image from 'next/image'

import Banner from "../components/banner/Banner";
import InputForm from "../components/inputForm/InputForm";
import Navbar from "../components/navbar/Navbar";
import { garamond } from "../layout";

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-between pl-16 pr-16 pt-6 pb-0">
      <Banner />
      <article className='w-screen'>
        <div className='h-[300px] w-full flex items-center justify-center overflow-hidden'>
          <Image
            src="https://picsum.photos/seed/picsum/200/300"
            alt="Picture of a city"
            height={300}
            width={300}
            className='w-full'
          />
        </div>
        <h2 className='p-8 pb-0'>City Name</h2>
        <p className={`${garamond.className} text-[20px] p-8 pt-0 pb-0`}>Lorem ipsum dolor sit amet consectetur adipisicing elit. Sunt neque minus molestias deleniti, quibusdam hic fugit incidunt, eos mollitia amet autem corrupti dicta. Fugit itaque consectetur assumenda incidunt eos voluptates!</p>
        <a href="" className='p-8 pt-0 text-[15px] text-sky-600'>Wikipedia</a>
      </article>
      <Navbar />
    </main>
  )
}
