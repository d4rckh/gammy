import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { QueryClientProvider } from '@tanstack/react-query'
import { queryClient } from './lib/api/clients.ts'
import LayoutWrapper from './components/layoutWrapper.tsx'
import { BrowserRouter, Routes, Route } from "react-router";
import IndexPage from './pages/Index.tsx'
import Achievements from './pages/Achievements.tsx'
import PlayerReports from './pages/PlayerReports.tsx'
import Leaderboards from './pages/Leaderboards.tsx'
import Interactions from './pages/Interactions.tsx'
import GameStats from './pages/GameStats.tsx'
import GameParameters from './pages/GameParameters.tsx'
import Players from './pages/Players.tsx'
import Player from './pages/Player.tsx'
import { ThemeProvider } from "@/components/themeProvider.tsx"

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <ThemeProvider defaultTheme="dark" storageKey="theme">
          <LayoutWrapper>
            <Routes>
              <Route path='/' element={<IndexPage />} />
              <Route path='/achievements' element={<Achievements />} />
              <Route path='/parameters' element={<GameParameters />} />
              <Route path='/stats' element={<GameStats />} />
              <Route path='/interactions' element={<Interactions />} />
              <Route path='/leaderboards' element={<Leaderboards />} />
              <Route path='/reports' element={<PlayerReports />} />
              <Route path='/players' element={<Players />} />
              <Route path='/players/:playerId' element={<Player />} />
            </Routes>
          </LayoutWrapper>
        </ThemeProvider>
      </BrowserRouter>
    </QueryClientProvider>
  </StrictMode>,
)
