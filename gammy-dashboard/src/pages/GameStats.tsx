import PageTitle from "@/components/pageTitle";
import GameStatsList from "@/components/GameStatsList";
import CreateOrEditStatDialog from "@/components/stats/CreateOrEditStatDialog";

export default function GameStats() {
  return <>
    <PageTitle title="Stats" description="Here you can track player stats and also see insights and various analytics of them." />

    <CreateOrEditStatDialog />

    <GameStatsList />
  </>;
}