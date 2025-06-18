import {useAchievements} from "@/lib/api/queries"
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "./ui/table";
import CreateOrEditAchievementDialog from "./achievements/CreateOrEditAchievementDialog";
import DeleteAchievementButton from "./achievements/DeleteAchievementButton";
import {NavLink} from "react-router";
import {Button} from "./ui/button";

export default function AchievementsList() {
    const achievements = useAchievements();

    return achievements.data && <>
        <Table className="mt-2">
            <TableHeader>
                <TableRow>
                    <TableHead>ID</TableHead>
                    <TableHead>API Name</TableHead>
                    <TableHead>Name</TableHead>
                    <TableHead>Description</TableHead>
                    <TableHead>Unlock condition</TableHead>
                    <TableHead>Actions</TableHead>
                </TableRow>
            </TableHeader>
            <TableBody>
                {
                    achievements.data.map(ach =>
                        <TableRow>
                            <TableCell>{ach.id}</TableCell>
                            <TableCell>{ach.apiName}</TableCell>
                            <TableCell>{ach.name}</TableCell>
                            <TableCell>{ach.description}</TableCell>
                            <TableCell>{ach.unlockExpression}</TableCell>
                            <TableCell className="flex flex-wrap gap-2">
                                <NavLink to={`/achievements/${ach.apiName}`}>
                                    <Button variant={"outline"} size={"sm"}>Analytics</Button>
                                </NavLink>
                                <CreateOrEditAchievementDialog achievement={ach}/>
                                <DeleteAchievementButton achievement={ach}/>
                            </TableCell>
                        </TableRow>
                    )
                }
            </TableBody>
        </Table>
    </>
}