import {Button} from "@/components/ui/button";
import {useLockPlayerAchievement, useUnlockPlayerAchievement} from "@/lib/api/mutations";

export default function LockOrUnlockPlayerAchievementButton({
                                                                playerId, apiName, value
                                                            }: {
    playerId: number,
    apiName: string,
    value?: boolean
}) {
    const unlockMutation = useUnlockPlayerAchievement(playerId);
    const lockMutation = useLockPlayerAchievement(playerId);

    function lock() {
        lockMutation.mutate({
            params: {
                path: {playerId, apiName}
            }
        })
    }

    function unlock() {
        unlockMutation.mutate({
            params: {
                path: {playerId, apiName}
            }
        })
    }

    if (value != undefined) {
        return <Button variant={"outline"} onClick={value ? lock : unlock}>{value ? "Lock" : "Unlock"}</Button>
    }
}