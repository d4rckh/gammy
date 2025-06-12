import { useEffect, useRef, type ReactNode } from "react";
import useLoginAsAdmin from "../lib/api/queries";
import { Button } from "./ui/button";
import { NavLink, useLocation } from "react-router";
import { ModeToggle } from "./ThemeToggle";
import clsx from "clsx"; // optional: for cleaner conditional classNames

const NAV_ITEMS = [
  { label: "Home", to: "/" },
  { label: "Players", to: "/players" },
  { label: "Achievements", to: "/achievements" },
  { label: "Stats", to: "/stats" },
  { label: "Leaderboards", to: "/leaderboards" },
  { label: "Interactions", to: "/interactions" },
  { label: "Parameters", to: "/parameters" },
  { label: "Reports", to: "/reports" },
];

export default function LayoutWrapper({ children }: { children: ReactNode }) {
  useLoginAsAdmin();

  return (
    <div className="max-w-6xl mx-auto my-3 px-4">
      <nav className="flex flex-wrap items-center gap-2 mb-4">
        <div className="font-black text-xl mr-2 text-primary">Gammy</div>

        {NAV_ITEMS.map(({ label, to }) => (
          <NavLink key={to} to={to}>
            {({ isActive }) => (
              <Button
                variant="link"
                className={clsx(
                  isActive && "text-foreground underline"
                )}
              >
                {label}
              </Button>
            )}
          </NavLink>
        ))}

        <div className="flex-1" />


        <div className="font-black">
          <ModeToggle />
        </div>
      </nav>

      <main>{children}</main>
    </div>
  );
}
