import PageTitle from "@/components/pageTitle";
import AchievementsList from "@/components/AchivementsList";
import CreateOrEditAchievementDialog from "@/components/achievements/CreateOrEditAchievementDialog";

export default function Achievements() {
  return <>
    <PageTitle title="Achievements" description="Here you can manage the achievements for your game." />

    <CreateOrEditAchievementDialog />

    <AchievementsList />
  </>;
}