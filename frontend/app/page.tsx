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
    cityDescription: "Stockholm ( Swedish: [ˈstɔ̂kː(h)ɔlm] ) is the capital and most populous city of Sweden as well as the largest urban area in the Nordic countries. Approximately 1 million people live in the municipality, with 2.1 million in the urban area, and 2.4 million in the metropolitan area. The city stretches across fourteen islands where Lake Mälaren flows into the Baltic Sea. Outside the city to the east, and along the coast, is the island chain of the Stockholm archipelago. The area has been settled since the Stone Age, in the 6th millennium BC, and was founded as a city in 1252 by Swedish statesman Birger Jarl. The city serves as the county seat of Stockholm County.",
    imageUrl: "https://upload.wikimedia.org/wikipedia/commons/3/3f/View_of_Stockholm-170351.jpg",
    wikiUrl: "http://en.wikipedia.org/?curid=26741"
  });

  return (
    <main className="flex min-h-screen flex-col items-center justify-between pl-16 pr-16 pt-6 pb-0">
      <Banner />
      {!isButtonClicked &&
        <>
          <section className={`${garamond.className} text-3xl text-black`}>
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
