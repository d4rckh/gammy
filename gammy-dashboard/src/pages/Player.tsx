import PageTitle from "@/components/pageTitle";
import { BanPlayerButton } from "@/components/players/actions/BanPlayerButton";
import CreatePlayerOverrideForm from "@/components/players/overrides/CreatePlayerOverrideForm";
import { PlayerAchievements } from "@/components/players/PlayerAchievements";
import { PlayerComputedParameters } from "@/components/players/PlayerComputedParameters";
import { PlayerOverrides } from "@/components/players/PlayerOverrides";
import { PlayerStats } from "@/components/players/PlayerStats";
import { ReportsByPlayer } from "@/components/players/ReportsByPlayer";
import { ReportsForPlayer } from "@/components/players/ReportsForPlayer";
import PlayerStatHistory from "@/components/players/PlayerStatHistory";
import SectionTitle from "@/components/sectionTitle";
import { Button } from "@/components/ui/button";
import { Dialog, DialogContent, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { usePlayer } from "@/lib/api/queries";
import { useParams } from "react-router";
import PlayerInteractionsList from "@/components/players/PlayerInteractions";

export default function Player() {
  const { playerId: playerParam } = useParams();
  const { data: player } = usePlayer(parseInt(playerParam ?? "0"));

  if (!player || !player.id) return <>Loading...</>;

  return (
    <>
      <PageTitle title={`Player - ${player.username}`} />

      <Tabs defaultValue="summary" className="mt-4">
        <TabsList className="grid w-full grid-cols-6 mb-4">
          <TabsTrigger value="summary">Summary</TabsTrigger>
          <TabsTrigger value="stats">Stats</TabsTrigger>
          <TabsTrigger value="achievements">Achievements</TabsTrigger>
          <TabsTrigger value="overrides">Overrides</TabsTrigger>
          <TabsTrigger value="reports">Reports</TabsTrigger>
          <TabsTrigger value="interactions">Interactions</TabsTrigger>
        </TabsList>

        <TabsContent value="summary">
          <BanPlayerButton player={player} />
        </TabsContent>

        <TabsContent value="interactions">
          <PlayerInteractionsList playerId={player.id} />
        </TabsContent>

        <TabsContent value="stats">
          <PlayerStats playerId={player.id} />

          <SectionTitle title="Stat History" />

          <PlayerStatHistory playerId={player.id} />
        </TabsContent>

        <TabsContent value="achievements">
          <PlayerAchievements playerId={player.id} />
        </TabsContent>

        <TabsContent value="overrides">
          <div className="flex gap-2 mb-2">
            <Dialog>
              <DialogTrigger asChild>
                <Button size="sm">View computed parameters</Button>
              </DialogTrigger>
              <DialogContent>
                <DialogTitle>Computed parameters</DialogTitle>
                <PlayerComputedParameters playerId={player.id} />
              </DialogContent>
            </Dialog>
            <CreatePlayerOverrideForm playerId={player.id} />
          </div>
          <PlayerOverrides playerId={player.id} />
        </TabsContent>

        <TabsContent value="reports">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <h3 className="text-lg font-semibold mb-2">Reports by Player</h3>
              <ReportsForPlayer playerId={player.id} />
            </div>
            <div>
              <h3 className="text-lg font-semibold mb-2">Reports for Player</h3>
              <ReportsByPlayer playerId={player.id} />
            </div>
          </div>
        </TabsContent>
      </Tabs>
    </>
  );
}
