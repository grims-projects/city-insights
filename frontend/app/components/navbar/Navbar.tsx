import Image from 'next/image'
import Link from 'next/link';

export default function Navbar() {
    return (
    <nav className='h-[84px] w-screen bg-gray sticky bottom-0'>
        <ul className="h-full flex justify-evenly items-center">
          <li>
            <Link href="../city">
                <Image
                    src="/icons/home.svg"
                    alt="Home page button"
                    width={40}
                    height={41}
                />
            </Link>
          </li>
          <li>
            <a href="/about">
                <Image
                    src="/icons/facts.svg"
                    alt="My facts button"
                    width={60}
                    height={41}
                />
            </a>
          </li>
        </ul>
    </nav>
    );
  }
