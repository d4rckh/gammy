import {useCreateGameStat, useUpdateGameStat} from "@/lib/api/mutations";
import type {components} from "@/lib/api/v1";
import {Dialog, DialogContent, DialogTitle, DialogTrigger} from "../ui/dialog";
import {Button} from "../ui/button";
import {useEffect, useState} from "react";
import {Input} from "../ui/input";
import {Label} from "../ui/label";
import {Checkbox} from "../ui/checkbox";
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from "../ui/select";

type GameStatEntity = components["schemas"]["GameStatEntity"];

export default function CreateOrEditStatDialog({
                                                   stat,
                                               }: {
    stat?: GameStatEntity;
}) {
    const create = useCreateGameStat();
    const update = useUpdateGameStat();

    const [open, setOpen] = useState(false);

    const [body, setBody] = useState<GameStatEntity>({
        apiName: "",
        displayName: "",
        description: "",
        defaultValue: 0,
        type: "INTEGER",
    });

    const [errors, setErrors] = useState<{ [key: string]: boolean }>({});

    useEffect(() => {
        if (stat) setBody(stat);
    }, [stat]);

    const handleChange = (field: keyof GameStatEntity, value: string | number | boolean | null) => {
        setBody((prev) => ({...prev, [field]: value}));
        setErrors((prev) => ({...prev, [field]: false}));
    };

    const parseOptionalNumber = (value: string): number | null => {
        const parsed = Number(value);
        return value === "" || isNaN(parsed) ? null : parsed;
    };

    const validateAndSubmit = () => {
        const newErrors: typeof errors = {};

        if (!body.apiName?.trim()) newErrors.apiName = true;
        if (!body.displayName?.trim()) newErrors.displayName = true;
        if (body.defaultValue === undefined || isNaN(body.defaultValue)) newErrors.defaultValue = true;
        if (body.type === undefined) newErrors.type = true;

        // Validate optional fields only if not null
        ["minValue", "maxValue", "maxChange"].forEach((key) => {
            const val = (body as any)[key];
            if (val !== null && val !== undefined && isNaN(val)) newErrors[key] = true;
        });

        setErrors(newErrors);
        if (Object.keys(newErrors).length > 0) return;

        if (stat) {
            update.mutateAsync({body}).then(a => setOpen(false));
        } else {
            create.mutateAsync({body}).then(a => setOpen(false));
        }
    };

    return (
        <Dialog open={open} onOpenChange={setOpen}>
            <DialogTrigger asChild>
                <Button size="sm">{stat ? "Edit" : "New Stat"}</Button>
            </DialogTrigger>
            <DialogContent>
                <DialogTitle>{stat ? "Edit Stat" : "New Stat"}</DialogTitle>

                <Label>API Name</Label>
                <Input
                    placeholder="API Name"
                    value={body.apiName ?? ""}
                    className={errors.apiName ? "border-red-500" : ""}
                    onChange={(e) => handleChange("apiName", e.target.value)}
                />

                <Label>Display Name</Label>
                <Input
                    placeholder="Display Name"
                    value={body.displayName ?? ""}
                    className={errors.displayName ? "border-red-500" : ""}
                    onChange={(e) => handleChange("displayName", e.target.value)}
                />

                <Label>Description</Label>
                <Input
                    placeholder="Description"
                    value={body.description ?? ""}
                    onChange={(e) => handleChange("description", e.target.value)}
                />

                <Label>Type</Label>
                <Select value={body.type ?? ""} onValueChange={(e) => handleChange("type", e)}>
                    <SelectTrigger className={"w-full"}>
                        <SelectValue placeholder={"Select stat type"}/>
                    </SelectTrigger>
                    <SelectContent>
                        <SelectItem value="INTEGER">INTEGER</SelectItem>
                        <SelectItem value="FLOAT">FLOAT</SelectItem>
                    </SelectContent>
                </Select>

                <Label>Default Value</Label>
                <Input
                    placeholder="Default Value"
                    type="number"
                    value={body.defaultValue ?? ""}
                    className={errors.defaultValue ? "border-red-500" : ""}
                    onChange={(e) => handleChange("defaultValue", parseInt(e.target.value))}
                />

                <Label>Min Value (optional)</Label>
                <Input
                    placeholder="Min Value"
                    type="number"
                    value={body.minValue ?? ""}
                    className={errors.minValue ? "border-red-500" : ""}
                    onChange={(e) => handleChange("minValue", parseOptionalNumber(e.target.value))}
                />

                <Label>Max Value (optional)</Label>
                <Input
                    placeholder="Max Value"
                    type="number"
                    value={body.maxValue ?? ""}
                    className={errors.maxValue ? "border-red-500" : ""}
                    onChange={(e) => handleChange("maxValue", parseOptionalNumber(e.target.value))}
                />

                <Label>Max Change (optional)</Label>
                <Input
                    placeholder="Max Change"
                    type="number"
                    value={body.maxChange ?? ""}
                    className={errors.maxChange ? "border-red-500" : ""}
                    onChange={(e) => handleChange("maxChange", parseOptionalNumber(e.target.value))}
                />

                <div className="flex items-center space-x-2 mt-2">
                    <Checkbox
                        checked={body.onlyIncrement ?? false}
                        onCheckedChange={(checked) => handleChange("onlyIncrement", !!checked)}
                    />
                    <Label>Only Increment</Label>
                </div>

                <Button onClick={validateAndSubmit}>{stat ? "Save" : "Create"}</Button>
            </DialogContent>
        </Dialog>
    );
}
