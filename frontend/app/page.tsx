"use client";

import { useState } from "react";
import Banner from "./components/banner/Banner";
import InputForm from "./components/inputForm/InputForm";
import Navbar from "./components/navbar/Navbar";
import { garamond } from "./layout";
import CityCard from "./components/cityCard/CityCard";

type CityCardProps = {
  cityName: string;
  cityDescription: string;
  imageUrl: string;
  wikiUrl: string;
};

export default function Home() {
  const [isButtonClicked, setIsButtonClicked] = useState(false);
  const [cityCardProps, setCityCardProps] = useState<CityCardProps>({
    cityName: "Stockholm",
    cityDescription: "Mina drömmars stad",
    imageUrl: "https://upload.wikimedia.org/wikipedia/commons/3/3f/View_of_Stockholm-170351.jpg",
    wikiUrl: "http://en.wikipedia.org/?curid=26741"
  });

  return (
    <main className="flex min-h-screen flex-col items-center justify-between pl-16 pr-16 pt-6 pb-0">
      <Banner />
      {!isButtonClicked &&
        <>
          <section className={`${garamond.className} text-3xl`}>
            <h1>
              Discover fascinating random facts from around the world at the click of a button!
            </h1>
          </section>
          <InputForm setIsButtonClicked={setIsButtonClicked} setCityCardProps={setCityCardProps} />
        </>
      }
      {isButtonClicked &&
      <CityCard cityName={cityCardProps.cityName} cityDescription={cityCardProps.cityDescription} imageUrl={cityCardProps.imageUrl} wikiUrl={cityCardProps.wikiUrl} />
      }
      <Navbar setIsButtonClicked={setIsButtonClicked}/>
    </main>
  )
}
