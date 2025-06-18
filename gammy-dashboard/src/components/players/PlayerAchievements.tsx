import {useAchievements, usePlayerAchievements} from "@/lib/api/queries";
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "../ui/table";
import LockOrUnlockPlayerAchievementButton from "./actions/LockOrUnlockPlayerAchievement";

export function PlayerAchievements({playerId}: { playerId: number }) {
    const {data} = usePlayerAchievements(playerId);
    const achievements = useAchievements();

    return data && <>
        <Table>
            <TableHeader>
                <TableRow>
                    <TableHead>Achievement API Name</TableHead>
                    <TableHead>Value</TableHead>
                    <TableHead>Actions</TableHead>
                </TableRow>
            </TableHeader>
            <TableBody>
                {
                    Object.keys(data).map(item =>
                        <TableRow>
                            <TableCell>{item}</TableCell>
                            <TableCell>{data[item] ? "yes" : "no"}</TableCell>
                            <TableCell>
                                {!achievements.data?.find(a => a.apiName == item)?.unlockExpression &&
                                    <LockOrUnlockPlayerAchievementButton playerId={playerId} apiName={item}
                                                                         value={data[item]}/>}
                            </TableCell>
                        </TableRow>
                    )
                }
            </TableBody>
        </Table>
    </>
}